package demo.kondeg.elevator;

/**
 * Created by kdegtiarenko on 2/10/2017.
 */

public class ElevatorError extends Exception {

    public ElevatorError(String message, Exception e) {
        super(message, e);
    }

    public ElevatorError(String message) {
        super(message);
    }


}
