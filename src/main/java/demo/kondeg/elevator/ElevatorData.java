package demo.kondeg.elevator;

/**
 * Created by kdegtiarenko on 2/10/2017.
 */
public class ElevatorData {

    private ElevatorStatus elevatorStatus;

    private ElevatorDoorStatus doorStatus;

    private int currentPosition;

    private int elevatorId;

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

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    public int getElevatorId() {
        return elevatorId;
    }

    public void setElevatorId(int elevatorId) {
        this.elevatorId = elevatorId;
    }
}
