package demo.kondeg.elevator;

/**
 * Created by kdegtiarenko on 2/10/2017.
 */
public class Elevator {

    private int elevatorId;

    private final int minFloor = 1;

    private int maxFloor;

    private ElevatorStatus elevatorStatus;

    private ElevatorDoorStatus doorStatus;

    private Elevator(){}

    public Elevator(int elevatorId, int maxFloor) {

        this.elevatorId = elevatorId;

        this.maxFloor = maxFloor;
    }

    public void start() {


    }

    private void openDoor() {

        System.out.println("Elevator "+getElevatorId()+" door open");

        this.doorStatus = ElevatorDoorStatus.DOOR_OPEN;
    }

    private void closeDoor() {

        System.out.println("Elevator "+getElevatorId()+" door closed");

        this.doorStatus = ElevatorDoorStatus.DOOR_CLOSED;

    }

    private void goToFloor(int floor) {


    }


    public ElevatorStatus getElevatorStatus() {
        return elevatorStatus;
    }

    public void setElevatorStatus(ElevatorStatus elevatorStatus) {
        this.elevatorStatus = elevatorStatus;
    }

    public ElevatorDoorStatus getDoorStatus() {
        return doorStatus;
    }

    public void setDoorStatus(ElevatorDoorStatus doorStatus) {
        this.doorStatus = doorStatus;
    }

    public int getElevatorId() {
        return elevatorId;
    }

    public void setElevatorId(int elevatorId) {
        this.elevatorId = elevatorId;
    }
}
