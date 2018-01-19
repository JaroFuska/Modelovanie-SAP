package sample;


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
    int n = 20;
    Random rnd = new Random();
    int w = 800, h = 600;
    ArrayList<Object> boxes;
    ArrayList<Text> texts;
    Thread thread;
    //delta_t should be in seconds
    double DELTA_T = 0.002;
    private TextArea ta_delta;
    private TextArea ta_pairs;
    private Label lab_pairs;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Timeline animation = null;
        panelWidth = 120;
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
        btn_startStop.setMinWidth(panelWidth);
        ta_delta = new TextArea(DELTA_T + " s");
        lab_pairs = new Label("Pairs:");
        ta_pairs = new TextArea();
        ta_delta.setMaxWidth(panelWidth);
        lab_pairs.setMaxWidth(panelWidth);
        ta_pairs.setMaxWidth(panelWidth);
        ta_delta.setEditable(false);
        ta_pairs.setEditable(false);
        ta_delta.setMaxHeight(10);
        ta_pairs.setMaxHeight(50);
        vb.getChildren().addAll(btn_startStop, ta_delta, lab_pairs, ta_pairs);
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

        animation = new Timeline(new KeyFrame(Duration.seconds(DELTA_T), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                long startTime = System.currentTimeMillis();
                String pairs = c.moveBoxes(DELTA_T);
                long finishTime = System.currentTimeMillis();
                DELTA_T = (finishTime - startTime);
                DELTA_T /= 1000;
                DELTA_T = DELTA_T == 0 ? 0.001 : DELTA_T;
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        ta_delta.setText(DELTA_T + " s");
                        ta_pairs.setText(pairs);
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

    }


    public static void main(String[] args) {
        launch(args);
    }
}

