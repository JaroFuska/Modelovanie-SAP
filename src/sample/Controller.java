package sample;

import javafx.geometry.Point2D;

public class Controller {

    public Controller() {

    }

    public void moveBox(Box b, double delta_t) {
        while(!b.move(explicitEulerNextPosition(b, delta_t))) {}
    }

    // POINT{t+1} = POINT{t} + delta_t*v(t)
    public Point2D explicitEulerNextPosition(Box b, double delta_t) {
        return b.getPosInMeters().add(b.getVectorInMeters().multiply(delta_t));
    }
}
