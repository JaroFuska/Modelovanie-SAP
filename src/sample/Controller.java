package sample;


import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Controller {
    private List<Object> boxes;
    private int mode;

    /**
     * Constructor of Controller class
     */
    public Controller() {
        boxes = Collections.synchronizedList(new ArrayList<>());
        mode = 0;
    }

    /**
     * Moves boxes
     *
     * @param delta_t time of algorithm run
     * @return coliding pairs
     */
    public String moveBoxes(double delta_t) {
        for (Object b : boxes) {
            if (mode == 0) {
                moveBox((Box) b, delta_t);
            } else {
                UniformGrid.updateBox((Box) b, delta_t);
            }
        }
        if (mode == 0) {
            return naiveCollisionDetection();
        } else {
            return gridCollisionDetection();
        }
    }

    /**
     * @param boxes list of boxes
     */
    public void setBoxes(ArrayList<Object> boxes) {
        this.boxes = boxes;
        for (Object b : boxes) {
            UniformGrid.addBox((Box) b);
        }
    }

    /**
     * Moves single box
     *
     * @param b       box to move
     * @param delta_t time difference
     */
    public void moveBox(Box b, double delta_t) {
        while (!b.move(explicitEulerNextPosition(b, delta_t))) {
        }
    }


    /**
     * Method to count next position by explicit Euler method
     * POINT{t+1} = POINT{t} + delta_t*v(t)
     *
     * @param b       box to move
     * @param delta_t time difference
     * @return new position of box
     */
    public Point2D explicitEulerNextPosition(Box b, double delta_t) {
        return b.getPosInMeters().add(b.getVectorInMeters().multiply(delta_t));
    }

    /**
     * @return colliding pairs
     */
    public String naiveCollisionDetection() {
        String pairs = "";
        for (int i = 0; i < boxes.size(); i++) {
            Box b = (Box) boxes.get(i);
            for (int j = i + 1; j < boxes.size(); j++) {
                Box b2 = (Box) boxes.get(j);
                if (b == b2) {
                    continue;
                }
                if (b.intersects(b2.getPos().getX(), b2.getPos().getY(), b2.getRectW(), b2.getRectH())) {
                    pairs += "(" + b.getText().getText() + ", " + b2.getText().getText() + ") ";
                }
            }
        }
        return pairs;
    }

    /**
     * @return colliding pairs
     */
    public String gridCollisionDetection() {
        String pairs = "";
        ArrayList<Cell> cells = UniformGrid.getCells();
        for (Cell cell : cells) {
            ArrayList<Box> cellBoxes = cell.getBoxes();
            if (cellBoxes.size() > 1) {
                for (int i = 0; i < cellBoxes.size(); i++) {
                    if (i != 0) {
                        pairs += "(" + cellBoxes.get(i - 1).getText().getText() + ", " + cellBoxes.get(i).getText().getText() + ") ";
                    }
                }
            }

        }
        return pairs;
    }

    /**
     * Changing collision detection mode
     *
     * @param p Pane
     */
    public void changeMode(Pane p) {
        if (mode == 0) {
            mode = 1;
            UniformGrid.makeGrid(p);
        } else {
            mode = 0;
            UniformGrid.deleteGrid(p);
        }
    }

    /**
     * @return collision detection mode
     */
    public int getMode() {
        return mode;
    }

}
