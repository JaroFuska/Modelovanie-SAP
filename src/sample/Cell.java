package sample;

import java.util.ArrayList;

public class Cell {

    private double x;
    private double y;
    private double width;
    private double height;
    private ArrayList<Box> boxes;

    /**
     * Constructor of class Cell
     *
     * @param x      position
     * @param y      position
     * @param width
     * @param height
     */
    public Cell(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        boxes = new ArrayList<Box>();
    }

    /**
     * Add new box in cell
     *
     * @param b new box
     */
    public void addBox(Box b) {
        boxes.add(b);
    }

    /**
     * Remove box b from cell
     *
     * @param b box to remove
     */
    public void removeBox(Box b) {
        boxes.remove(b);
    }

    /**
     * Returning boxes in this cell
     *
     * @return list of boxes in this cell
     */
    public ArrayList<Box> getBoxes() {
        return boxes;
    }

    /**
     * @return X position
     */
    public double getX() {
        return x;
    }

    /**
     * @return Y position
     */
    public double getY() {
        return y;
    }

    /**
     * @return width of cell
     */
    public double getWidth() {
        return width;
    }

    /**
     * @return height of cell
     */
    public double getHeight() {
        return height;
    }
}
