package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Random;


/**
 * Created by Jaro on 2.12.2017.
 */
public class Box extends Rectangle {
    private double dx;
    private double dy;
    private double w;
    private double h;
    private Random rnd;

    public Box(double x, double y, double w, double h) {
        this.setPos(x, y);
        this.w = w;
        this.h = h;
        this.setStroke(Color.BLACK);
        this.setHeight((double)new Random().nextInt(25)+10);
        this.setWidth((double)new Random().nextInt(25)+10);
        this.rnd = new Random();
        do {
            this.dx = (rnd.nextInt(21) - 10) / 10.0;
            this.dy = (rnd.nextInt(21) - 10) / 10.0;
        } while ((Math.abs(dx) < 0.2 || Math.abs(dy) < 0.2));
        this.toFront();
    }

    public void setPos(double x, double y) {
        setX(x);
        setY(y);
    }


    public void move(int delta_t){
        double x = getX()+dx;
        double y = getY()+dy;
        if (x <= 0 || x >= w-getWidth()) {
            dx *= -1;
            x = getX()+dx;
        }
        if (y <= 0 || y >= h-getHeight()) {
            dy *= -1;
            y = getY()+dy;
        }
        setPos(x, y);
    }

}
