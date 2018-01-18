package sample;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class UniformGrid {

    private double rowCount;
    private double colCount;
    private double screenW;
    private double screenH;
    private ArrayList<Cell> cells;

    public UniformGrid(double rowCount, double colCount, double screenW, double screenH) {
        this.rowCount = rowCount;
        this.colCount = colCount;
        this.screenW = screenW;
        this.screenH = screenH;
    }

    private void createCells(){
        int cellWidth = (int)(screenW/colCount);
        int cellHeight = (int)(screenW/rowCount);
        for (int i=0; i<rowCount; i++){
            for (int j=0; j<colCount; j++){
                Cell cell = new Cell(i*cellWidth, j*cellHeight, cellWidth, cellHeight);
                cells.add(cell);
            }
        }
    }

    private void addBox(Box b){
        for(Cell i:cells){
            Rectangle r = new Rectangle((int)i.getX(), (int)i.getY(), (int)i.getWidth(), (int)i.getHeight());
            if (r.intersects(b.getPos().getX(), b.getPos().getY(), b.getRectW(), b.getRectH())){
                i.addBox(b);
            }

        }
    }

    private void removeBox(Box b){
        for(Cell i:cells){
            if (i.getBoxes().contains(b)){
                i.removeBox(b);
            }

        }
    }

    private void updateBox(Box b, double stepX, double stepY){ //nevedel som ako to nazvat stepX a Y udavaju kolko sa posuva led tak premenuj
        removeBox(b);
        //b.setPos((b.getPos().getX()+stepX, b.getPos().getY()+stepY));
    }
}
