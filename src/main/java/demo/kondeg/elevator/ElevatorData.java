package demo.kondeg.elevator;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by kdegtiarenko on 2/10/2017.
 */
public class ElevatorData {

    private ElevatorStatus elevatorStatus;

    private ElevatorDoorStatus doorStatus;

    private int currentPosition;

    private int elevatorId;

    private int numberOfFloorTraveled = 0;

    private int numberOfTrips = 0;

    Queue<Request> requestQueue = new ConcurrentLinkedQueue<Request>();

    private ElevatorData() {

    }

    public ElevatorData(int elevatorId) {
        this.doorStatus = ElevatorDoorStatus.DOOR_CLOSED;
        currentPosition = 1;
        elevatorStatus = ElevatorStatus.IDLE;
        numberOfFloorTraveled = 0;
        numberOfTrips = 0;
        this.elevatorId = elevatorId;
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

    public int getNumberOfFloorTraveled() {
        return numberOfFloorTraveled;
    }

    public void setNumberOfFloorTraveled(int numberOfFloorTraveled) {
        this.numberOfFloorTraveled = numberOfFloorTraveled;
    }

    public int getNumberOfTrips() {
        return numberOfTrips;
    }

    public void setNumberOfTrips(int numberOfTrips) {
        this.numberOfTrips = numberOfTrips;
    }


}
