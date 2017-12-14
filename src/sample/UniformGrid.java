package sample;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class UniformGrid {

    private double cellSize;
    private double screenW;
    private double screenH;
    private ArrayList<Cell> cells;

    public UniformGrid(double cellSize, double screenW, double screenH) {
        this.cellSize = cellSize;
        this.screenW = screenW;
        this.screenH = screenH;
    }

    private void createCells(){
        int rowSum = (int)(screenW/cellSize);
        int columnSum = (int)(screenW/cellSize);
        for (int i=0; i<rowSum; i++){
            for (int j=0; j<columnSum; j++){
                Cell cell = new Cell(i*cellSize, j*cellSize, cellSize);
                cells.add(cell);
            }
        }
    }

    private void addBox(Box b){
        for(Cell i:cells){
            Rectangle r = new Rectangle((int)i.getX(), (int)i.getY(), (int)i.getSize(), (int)i.getSize());
            if (r.intersects(b.getPos().getX(), b.getPos().getY(), b.getRectW(), b.getRectH())){
                i.addBox(b);
            }

        }
    }
}
