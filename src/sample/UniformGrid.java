package sample;

import java.awt.*;
import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Arrays;

public class UniformGrid {

    private static double rowCount;
    private static double colCount;
    private static double screenW;
    private static double screenH;
    private static ArrayList<Cell> cells;
    private static ArrayList<Line> lines;

    public static ArrayList<Cell> getCells() {
        return cells;
    }

    public static void createCells(){
        screenH = 600;
        screenW = 800;
        rowCount = 10;
        colCount = 10;
        cells = new ArrayList<Cell>();
        lines = new ArrayList<Line>();

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

    public static void makeGrid(Pane p){
        for (int i=0; i<=colCount; i++){
            Line l = new Line(i*screenW/colCount, 0, i*screenW/colCount, screenH);
            Line l2 = new Line(0, i*screenH/rowCount, screenW, i*screenH/rowCount);
            l.setStroke(Color.GRAY);
            l2.setStroke(Color.GRAY);
            l.setStrokeWidth(0.3);
            l2.setStrokeWidth(0.3);
            lines.add(l);
            p.getChildren().add(l);
            lines.add(l2);
            p.getChildren().add(l2);
        }
    }

    public static void deleteGrid(Pane p){
        for (Line l:lines){
            p.getChildren().remove(l);
        }
    }
}
