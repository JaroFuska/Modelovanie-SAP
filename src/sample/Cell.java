package sample;

import java.util.ArrayList;

public class Cell {

    private double x;
    private double y;
    private double width;
    private double height;
    private ArrayList<Box> boxes;

    public Cell(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        boxes = new ArrayList<Box>();
    }

    public void addBox(Box b){
        boxes.add(b);
    }

    public ArrayList<Box> getBoxes() {
        return boxes;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }



}
