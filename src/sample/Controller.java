package sample;


import javafx.geometry.Point2D;

import java.util.ArrayList;

public class Controller {
    private ArrayList<Box> boxes = new ArrayList<>();

    public Controller() {

    }

    public void setBoxes(ArrayList<Box> boxes) {
        this.boxes = boxes;
    }

    public void moveBox(Box b, double delta_t) {
        while (!b.move(explicitEulerNextPosition(b, delta_t))) {
        }
        naiveCollisionDetection();
    }

    // POINT{t+1} = POINT{t} + delta_t*v(t)
    public Point2D explicitEulerNextPosition(Box b, double delta_t) {
        return b.getPosInMeters().add(b.getVectorInMeters().multiply(delta_t));
    }

    public void naiveCollisionDetection(){
        for (Box b : boxes) {
            for (Box b2 : boxes) {
                if (b == b2){
                    continue;
                }
                if (b.intersects(b2.getPos().getX(), b2.getPos().getY(), b2.getRectW(), b2.getRectH())){
                    System.out.println("Collision between " + b.getText().getText() + " and " + b2.getText().getText()+
                    " registered by NxN collision detection");
                }
            }
        }
    }

}
