package sample;

import java.awt.*;
import javafx.geometry.Point2D;
import java.util.ArrayList;
import java.util.Arrays;

public class UniformGrid {

    private static double rowCount;
    private static double colCount;
    private static double screenW;
    private static double screenH;
    private static ArrayList<Cell> cells;

    public UniformGrid(double rowCount, double colCount, double screenW, double screenH) {
        this.rowCount = rowCount;
        this.colCount = colCount;
        this.screenW = screenW;
        this.screenH = screenH;
        cells = new ArrayList<Cell>();
    }

    public static ArrayList<Cell> getCells() {
        return cells;
    }

    public static void createCells(){
        //tmp
        screenH = 600;
        screenW = 800;
        rowCount = 20;
        colCount = 20;
        cells = new ArrayList<Cell>();

        int cellWidth = (int)(screenW/colCount);
        int cellHeight = (int)(screenW/rowCount);
        for (int i=0; i<rowCount; i++){
            for (int j=0; j<colCount; j++){
                Cell cell = new Cell(i*cellWidth, j*cellHeight, cellWidth, cellHeight);
                cells.add(cell);
            }
        }
    }

    public static  void addBox(Box b){
        for(Cell i:cells){
            Rectangle r = new Rectangle((int)i.getX(), (int)i.getY(), (int)i.getWidth(), (int)i.getHeight());
            if (r.intersects(b.getPos().getX(), b.getPos().getY(), b.getRectW(), b.getRectH())){
                i.addBox(b);
            }

        }
    }

    public static void removeBox(Box b){
        for(Cell i:cells){
            if (i.getBoxes().contains(b)){
                i.removeBox(b);
            }

        }
    }

    public static void updateBox(Box b, double delta_t){
        removeBox(b);
        b.move(b.getPosInMeters().add(b.getVectorInMeters().multiply(delta_t)));
        addBox(b);
    }
}
