package sample;

import java.util.ArrayList;

public class Cell {

    private double x;
    private double y;
    private double size;
    private ArrayList<Box> boxes;

    public Cell(double x, double y, double size) {
        this.x = x;
        this.y = y;
        this.size = size;
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

    public double getSize() {
        return size;
    }



}
