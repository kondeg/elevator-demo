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

    private Queue<Request> requestQueue = new ConcurrentLinkedQueue<Request>();

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

    public Response processRequest(Request request) {
        Response response = new Response(elevatorId);
        if (elevatorStatus == ElevatorStatus.IDLE) {
            int floorsAway = Math.abs(currentPosition - request.getFloorRequested());
            response.setFloorsAway(floorsAway);
            if (response.getFloorsAway()==0) {
                response.setResponseCode(Response.ResponseCode.IDLE_SAME_FLOOR);
            } else {
                response.setResponseCode(Response.ResponseCode.IDLE);
            }
        } else if (elevatorStatus == ElevatorStatus.MOVING_DOWN) {
            if (currentPosition >= request.getFloorRequested()) {
                response.setResponseCode(Response.ResponseCode.PASSING);
                response.setFloorsAway(currentPosition - request.getFloorRequested());
            }
        } else if (elevatorStatus == ElevatorStatus.MOVING_UP) {
            if (currentPosition <= request.getFloorRequested()) {
                response.setResponseCode(Response.ResponseCode.PASSING);
                response.setFloorsAway(currentPosition - request.getFloorRequested());
            }
        } else {

            //Do some other algorithm

        }
        return response;
    }

    public void addToQueue(Request request) {

        requestQueue.add(request);

    }

    public Request removeFromQueue() {

        return requestQueue.remove();

    }

    public boolean isQueueEmpty() {

        return requestQueue.isEmpty();
    }



    public synchronized ElevatorStatus getElevatorStatus() {
        return elevatorStatus;
    }

    public synchronized void setElevatorStatus(ElevatorStatus elevatorStatus) {
        this.elevatorStatus = elevatorStatus;
    }

    public synchronized ElevatorDoorStatus getDoorStatus() {
        return doorStatus;
    }

    public synchronized void setDoorStatus(ElevatorDoorStatus doorStatus) {
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
