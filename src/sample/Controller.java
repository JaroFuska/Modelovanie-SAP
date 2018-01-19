package sample;


import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Controller {
    private List<Object> boxes;

    public Controller() {
        boxes = Collections.synchronizedList(new ArrayList<>());
    }

    public String moveBoxes(double delta_t) {
        for (Object b : boxes) {
            moveBox((Box)b, delta_t);
        }
        return naiveCollisionDetection();
    }

    public void setBoxes(ArrayList<Object> boxes) {
        this.boxes = boxes;
    }


    public void moveBox(Box b, double delta_t) {
        while (!b.move(explicitEulerNextPosition(b, delta_t))) {
        }
    }

    // POINT{t+1} = POINT{t} + delta_t*v(t)
    public Point2D explicitEulerNextPosition(Box b, double delta_t) {
        return b.getPosInMeters().add(b.getVectorInMeters().multiply(delta_t));
    }

    /*This is not exactly n^2 colision detection
    * more like n!*/
    public String naiveCollisionDetection(){
        String pairs = "";
        for (int i = 0; i < boxes.size(); i++) {
            Box b = (Box) boxes.get(i);
            for (int j = i+1; j < boxes.size(); j++) {
                Box b2 = (Box) boxes.get(j);
                if (b == b2){
                    continue;
                }
                if (b.intersects(b2.getPos().getX(), b2.getPos().getY(), b2.getRectW(), b2.getRectH())){
                    pairs += "(" + b.getText().getText() + ", " + b2.getText().getText() + ") ";
//                    System.out.println("Collision between " + b.getText().getText() + " and " + b2.getText().getText()+
//                    " registered by NxN collision detection");
                }
            }
        }
        return pairs;
    }


}
