package sample;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

public class Main extends Application implements Runnable {
    private final Controller c = new Controller();
    Pane pg;
    double panelWidth;
    int n = 100;
    Random rnd = new Random();
    int w = 800, h = 600;
    ArrayList<Box> boxes;
    ArrayList<Text> texts;
    Thread thread;
    //delta_t should be in seconds
    double DELTA_T = 0.002;
    private TextArea ta_delta;

    private boolean pause = false;

    @Override
    public synchronized void start(Stage primaryStage) throws Exception {
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
        btn_startStop.setOnMouseClicked(event -> {
            boolean start = btn_startStop.getText() == "Stop" ? false : true;
            btn_startStop.setText(start ? "Stop" : "Start");
            //TODO - not working
            if (start) {
                thread.start();
            } else {
                thread.interrupt();
            }
        });
        ta_delta = new TextArea(DELTA_T + " s");
        ta_delta.setMaxWidth(panelWidth);
        ta_delta.setEditable(false);
        ta_delta.setMaxHeight(10);
        //TODO add some menu to the vb maybe
        //TODO set width of menu components to panelWidth
        vb.getChildren().addAll(btn_startStop, ta_delta);
        borderPane.setRight(vb);

        for (int i = 0; i < n; i++) {
            Box b = new Box((double) rnd.nextInt(w - 40 - (int)panelWidth), (double) rnd.nextInt(h - 40), w - panelWidth, h, "" + i);
            boxes.add(b);
            texts.add(b.getText());
        }
        pg.getChildren().addAll(boxes);
        pg.getChildren().addAll(texts);

        c.setBoxes(boxes);


        thread = new Thread(this);
        thread.start();
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
    }


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void run() {
        long startTime;
        while (true) {
            try {
                //TODO make it without sleep by counting delta_t
//                thread.sleep((long)DELTA_T * 1000);
            } catch (Exception e) {
                // TODO: handle exception
            }
            startTime = System.currentTimeMillis();
            for (Box b : boxes) {
                c.moveBox(b, DELTA_T);
            }
            long finishTime = System.currentTimeMillis();
            DELTA_T = (finishTime - startTime);
            DELTA_T /= 1000;
            ta_delta.setText(DELTA_T + " s");
//            DELTA_T = DELTA_T == 0 ? 0.001 : DELTA_T;
//            System.out.println(DELTA_T);
//            System.out.println(startTime);
//            System.out.println(finishTime);

        }
    }
}

