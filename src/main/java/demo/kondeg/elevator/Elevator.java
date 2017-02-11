package demo.kondeg.elevator;

/**
 * Created by kdegtiarenko on 2/10/2017.
 */
public class Elevator {

    private int elevatorId;

    private final int minFloor = 1;

    private int maxFloor;

    private Elevator(){}

    public Elevator(int elevatorId, int maxFloor) {

        this.elevatorId = elevatorId;

        this.maxFloor = maxFloor;
    }




}
