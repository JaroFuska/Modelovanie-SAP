package sample;


import com.sun.javafx.perf.PerformanceTracker;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Random;

public class Main extends Application {
    private final Controller c = new Controller();
    Pane pg;
    double panelWidth;
    /**
     * Number of objects
     */
    int n = 150;
    Random rnd = new Random();
    int w = 1000, h = 600;
    ArrayList<Object> boxes;
    ArrayList<Text> texts;
    Thread thread;
    //delta_t in seconds
    double DELTA_T = 0.002;
    private TextArea ta_delta;
    private TextArea ta_pairs;
    private TextArea ta_avGrid;
    private TextArea ta_avNaive;
    private Label lab_pairs;
    private Label lab_avGrid;
    private Label lab_avNaive;
    private static PerformanceTracker tracker;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Timeline animation = null;
        UniformGrid.createCells();
        panelWidth = 200;
        boxes = new ArrayList();
        texts = new ArrayList();
        primaryStage.setTitle("Sweep And Prune");
        BorderPane borderPane = new BorderPane();
        Scene root = new Scene(borderPane, w, h);
        pg = new Pane();
        pg.setMinHeight(root.getHeight());
        pg.setMaxWidth(root.getWidth() - panelWidth);
        VBox vb = new VBox();

        Button btn_startStop = new Button("Stop");
        Button btn_changeMode = new Button("Change Method");
        btn_startStop.setMinWidth(panelWidth);
        btn_changeMode.setMinWidth(panelWidth);
        ta_delta = new TextArea(DELTA_T + " s");
        lab_pairs = new Label("Pairs:");
//        lab_avNaive = new Label("Average fps for naive algorithm:");
        lab_avNaive = new Label("Actual fps for naive algorithm:");
//        lab_avGrid = new Label("Average fps for grid algorithm:");
        lab_avGrid = new Label("Actual fps for grid algorithm:");
        ta_pairs = new TextArea();
        ta_avNaive = new TextArea();
        ta_avGrid = new TextArea();
        ta_delta.setMaxWidth(panelWidth);
        ta_avNaive.setMaxWidth(panelWidth);
        ta_avGrid.setMaxWidth(panelWidth);
        lab_pairs.setMaxWidth(panelWidth);
        lab_avNaive.setMaxWidth(panelWidth);
        lab_avGrid.setMaxWidth(panelWidth);
        ta_pairs.setMaxWidth(panelWidth);
        vb.setMaxWidth(panelWidth);
        ta_pairs.setWrapText(true);
        ta_pairs.setMinHeight(400);
        ta_delta.setEditable(false);
        ta_pairs.setEditable(false);
        ta_avNaive.setEditable(false);
        ta_avGrid.setEditable(false);
        ta_delta.setMaxHeight(10);
        ta_avNaive.setMaxHeight(10);
        ta_avGrid.setMaxHeight(10);
        ta_pairs.setMaxHeight(50);
        vb.getChildren().addAll(btn_startStop, btn_changeMode, ta_delta, lab_pairs, ta_pairs, lab_avNaive, ta_avNaive, lab_avGrid, ta_avGrid);
        borderPane.setRight(vb);

        for (int i = 0; i < n; i++) {
            Box b = new Box((double) rnd.nextInt(w - 40 - (int) panelWidth), (double) rnd.nextInt(h - 40), w - panelWidth, h, "" + i);
            boxes.add(b);
            texts.add(b.getText());
            pg.getChildren().add(b);
        }
        pg.getChildren().addAll(texts);

        c.setBoxes(boxes);

        borderPane.setCenter(pg);
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });
        primaryStage.setScene(root);
        primaryStage.show();

        tracker = PerformanceTracker.getSceneTracker(root);
        /**
         * Animation timer
         */
        animation = new Timeline(new KeyFrame(Duration.seconds(DELTA_T), new EventHandler<ActionEvent>() {
            Long gridCount = 0L;
            Long naiveCount = 0L;
            double gridSum = 0;
            double naiveSum = 0;
            double naiveFPS = 0;
            double gridFPS = 0;

            @Override
            public void handle(ActionEvent event) {
                long startTime = System.currentTimeMillis();
                String pairs = c.moveBoxes(DELTA_T);
                float fps = getFPS();
                if (c.getMode() == 0) {
                    if (fps > 0) {
                        naiveCount++;
                        naiveSum += fps;
                        naiveFPS = fps;
                    }
                } else {
                    if (fps > 0) {
                        gridCount++;
                        gridSum += fps;
                        gridFPS = fps;
                    }
                }
                long finishTime = System.currentTimeMillis();
                DELTA_T = (finishTime - startTime);
                DELTA_T /= 1000;
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        ta_delta.setText(DELTA_T + " s");
                        ta_pairs.setText(pairs);
                        if (fps > 0) {
//                            ta_avNaive.setText(naiveSum / naiveCount + "");
                            ta_avNaive.setText(naiveFPS + "");
//                            ta_avGrid.setText(gridSum / gridCount + "");
                            ta_avGrid.setText(gridFPS + "");
                        }
                    }
                });
            }
        }));
        Timeline finalAnimation = animation;
        btn_startStop.setOnMouseClicked(event -> {
            boolean start = btn_startStop.getText() == "Stop" ? false : true;
            btn_startStop.setText(start ? "Stop" : "Start");
            if (start) {
                finalAnimation.play();
            } else {
                finalAnimation.pause();
            }
        });
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play();

        btn_changeMode.setOnMouseClicked(event -> {
            c.changeMode(pg);
        });

    }

    /**
     * @return fps
     */
    private float getFPS() {
        float fps = tracker.getAverageFPS();
        tracker.resetAverageFPS();
        return fps;
    }

    public static void main(String[] args) {
        launch(args);
    }
}

