package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

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
    Thread thread;
    //delta_t should be in seconds
    double DELTA_T = 0.002;

    @Override
    public void start(Stage primaryStage) throws Exception{
        panelWidth = 120;
        primaryStage.setTitle("Sweep And Prune");
        BorderPane borderPane = new BorderPane();
        Scene root = new Scene(borderPane, w, h);
        pg = new Pane();
        pg.setMinHeight(root.getHeight());
        pg.setMinWidth(root.getWidth()-panelWidth);
        VBox vb = new VBox();
        //TODO add some menu to the vb maybe
        //TODO set width of menu components to panelWidth
        borderPane.setLeft(vb);


        boxes = new ArrayList();
        for (int i = 0; i < n; i++) {
            boxes.add(new Box((double)rnd.nextInt(w-40), (double)rnd.nextInt(h-40), w, h));
        }
        pg.getChildren().addAll(boxes);




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
        //TODO move boxes
        while(true) {
            try {
                //TODO make it without sleep by counting delta_t
                thread.sleep(20);
            } catch (Exception e) {
                // TODO: handle exception
            }
            //TODO caunt delta_t here
            for (Box b : boxes
                 ) {
                c.moveBox(b, DELTA_T);

            }
        }
    }
}
