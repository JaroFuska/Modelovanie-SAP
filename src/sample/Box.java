package sample;


import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.Random;

public class Box extends Rectangle {
    private Text text;
    private final double PIXELS_PER_METER = 3780;   //rounded actual value
    private double w;   //width of screen in pixels
    private double h;   //height of screen in pixels
    private Random rnd;
    private Point2D pos;    //position is in pixels
    private Point2D vector; //vector representing speed and direction in pixels
    private double rectW; //width of rectangle in pixels
    private double rectH; //height of rectangle in pixels
    private int ID;

    /**
     * Constructor making box in position x, y with random width and height and random speed in random direction
     *
     * @param x             position x of box
     * @param y             position y of box
     * @param screen_width  width of main screen
     * @param screen_height height of main screen
     */
    public Box(double x, double y, double screen_width, double screen_height, String name) {
        this.ID = Integer.parseInt(name);
        this.rnd = new Random();
        this.pos = new Point2D(x, y);
        this.setPos(pos);
        this.w = screen_width;
        this.h = screen_height;
        this.setFill(Color.color(rnd.nextDouble(), rnd.nextDouble(), rnd.nextDouble()).darker());
        this.rectH = (double) new Random().nextInt(25) + 10;
        this.rectW = (double) new Random().nextInt(25) + 10;
        this.setHeight(this.rectH);
        this.setWidth(this.rectW);
        this.text = new Text(x + rectH / 2, y + rectW / 2, name);
        this.text.setStroke(Color.WHITE);
        double dx, dy;
        do {
            dx = (rnd.nextInt(500) - 250);
            dy = (rnd.nextInt(500) - 250);
        } while ((Math.abs(dx) < 20 || Math.abs(dy) < 20));
        vector = new Point2D(dx, dy);
        this.toFront();
    }

    /**
     * Setting position of box to point p
     *
     * @param p
     */
    public void setPos(Point2D p) {
        setX(p.getX());
        setY(p.getY());
        this.pos = p;
    }

    /**
     * @return position of box
     */
    public Point2D getPos() {
        return pos;
    }

    /**
     * @return vector representing velocity and direction
     */
    public Point2D getVector() {
        return vector;
    }

    /**
     * @return position of box in meters
     */
    public Point2D getPosInMeters() {
        return pos.multiply(1 / PIXELS_PER_METER);
    }

    /**
     * @return vector in meters
     */
    public Point2D getVectorInMeters() {
        return vector.multiply(1 / PIXELS_PER_METER);
    }

    /**
     * @return width of box
     */
    public double getRectW() {
        return rectW;
    }

    /**
     * @return height of box
     */
    public double getRectH() {
        return rectH;
    }

    /**
     * @return Text element representing ID of box
     */
    public Text getText() {
        return text;
    }

    /**
     * Method that moves box to new location
     *
     * @param new_point new point to move box to
     * @return True if new location is OK else False
     */
    public boolean move(Point2D new_point) {
        new_point = new_point.multiply(PIXELS_PER_METER);
        if (new_point.getX() < 0 || new_point.getX() > w - getWidth()) {
            setPos(new Point2D(new_point.getX() <= 0 ? 0 : w - getWidth(), getY()));
            vector = new Point2D(vector.getX() * -1, vector.getY());
            return false;
        }
        if (new_point.getY() < 0 || new_point.getY() > h - getHeight()) {
            setPos(new Point2D(getX(), new_point.getY() <= 0 ? 0 : h - getHeight()));
            vector = new Point2D(vector.getX(), vector.getY() * -1);
            return false;
        }
        setPos(new_point);
        text.setX(new_point.getX());
        text.setY(new_point.getY() + rectH);
        return true;
    }

}
