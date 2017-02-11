package demo.kondeg.elevator;

/**
 * Created by kdegtiarenko on 2/10/2017.
 */
public class Elevator {

    private final int minFloor = 1;

    private int maxFloor;

    private ElevatorData elevatorData;

    private int elevatorId;


    private Elevator(){}

    public Elevator(int elevatorId, int maxFloor, ElevatorData elevatorData) {

        this.elevatorId = elevatorId;

        this.maxFloor = maxFloor;

        this.elevatorData = elevatorData;
    }

    public void start() {


    }

    private void openDoor() {

        System.out.println("Elevator "+getElevatorId()+" door open");

        elevatorData.setDoorStatus(ElevatorDoorStatus.DOOR_OPEN);

    }

    private void closeDoor() {

        System.out.println("Elevator "+getElevatorId()+" door closed");

        elevatorData.setDoorStatus(ElevatorDoorStatus.DOOR_CLOSED);

    }

    private void goToFloor(int floor) throws ElevatorError {
        if (floor<minFloor || floor>maxFloor) {

            throw new ElevatorError("Invalid floor number "+floor);

        }

    }


    public int getElevatorId() {
        return elevatorId;
    }

    public void setElevatorId(int elevatorId) {
        this.elevatorId = elevatorId;
    }
}
