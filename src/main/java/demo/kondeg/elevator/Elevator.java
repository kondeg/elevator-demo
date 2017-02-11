package demo.kondeg.elevator;

import java.util.Map;

/**
 * Created by kdegtiarenko on 2/10/2017.
 */
public class Elevator implements Runnable {

    private final int minFloor = 1;

    private int maxFloor;

    Map<Elevator, ElevatorData> sharedStatusMap;

    private int elevatorId;

    Request currentRequest = null;


    private Elevator(){}

    public Elevator(int elevatorId, int maxFloor, Map<Elevator, ElevatorData> sharedStatusMap) {

        this.elevatorId = elevatorId;

        this.maxFloor = maxFloor;

        this.sharedStatusMap = sharedStatusMap;
    }

    public void run() {

        System.out.println("Starting elevator "+getElevatorId());

        System.out.println("Elevator Status "+sharedStatusMap.get(this).getElevatorStatus());

        while (!sharedStatusMap.get(this).getElevatorStatus().equals(ElevatorStatus.MAINTENANCE)) {

            if (!sharedStatusMap.get(this).isQueueEmpty()) {

                currentRequest = sharedStatusMap.get(this).removeFromQueue();

                System.out.println("Dispatching elevator "+getElevatorId()+" to "+currentRequest.getFloorRequested());

                closeDoor();

                try {

                    goToFloor(currentRequest.getFloorRequested());

                } catch (ElevatorError e) {
                    //DO some error handling
                    e.printStackTrace();
                }

                openDoor();


                sharedStatusMap.get(this).setElevatorStatus(ElevatorStatus.IDLE);

            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("--Stopping elevator "+getElevatorId());
    }

    private void openDoor() {

        System.out.println("Elevator "+getElevatorId()+" door open");

        sharedStatusMap.get(this).setDoorStatus(ElevatorDoorStatus.DOOR_OPEN);

    }

    private void closeDoor() {

        System.out.println("Elevator "+getElevatorId()+" door closed");

        sharedStatusMap.get(this).setDoorStatus(ElevatorDoorStatus.DOOR_CLOSED);

    }

    private void goToFloor(int floor) throws ElevatorError {
        if (floor<minFloor || floor>maxFloor) {

            throw new ElevatorError("Invalid floor number "+floor);

        }
        if (sharedStatusMap.get(this).getCurrentPosition()>floor) {
            sharedStatusMap.get(this).setElevatorStatus(ElevatorStatus.MOVING_DOWN);
        } else if (sharedStatusMap.get(this).getCurrentPosition()<floor) {
            sharedStatusMap.get(this).setElevatorStatus(ElevatorStatus.MOVING_UP);
        }

        while(floor!=sharedStatusMap.get(this).getCurrentPosition()) {

            if(sharedStatusMap.get(this).getElevatorStatus().equals(ElevatorStatus.MOVING_UP)) {
                sharedStatusMap.get(this).setCurrentPosition(sharedStatusMap.get(this).getCurrentPosition()+1);
                sharedStatusMap.get(this).setNumberOfFloorTraveled(sharedStatusMap.get(this).getNumberOfFloorTraveled()+1);
                System.out.println("Elevator "+getElevatorId()+" Position: "+sharedStatusMap.get(this).getCurrentPosition()+" Destination: "+floor+" Number Of Floors Traveled: "+sharedStatusMap.get(this).getNumberOfFloorTraveled());

            } else if(sharedStatusMap.get(this).getElevatorStatus().equals(ElevatorStatus.MOVING_DOWN)) {
                sharedStatusMap.get(this).setCurrentPosition(sharedStatusMap.get(this).getCurrentPosition()-1);
                sharedStatusMap.get(this).setNumberOfFloorTraveled(sharedStatusMap.get(this).getNumberOfFloorTraveled()+1);
                System.out.println("Elevator "+getElevatorId()+" Position: "+sharedStatusMap.get(this).getCurrentPosition()+" Destination: "+floor+" Number Of Floors Traveled: "+sharedStatusMap.get(this).getNumberOfFloorTraveled());
            }

        }

        int numberOfTrips = sharedStatusMap.get(this).getNumberOfTrips()+1;
        sharedStatusMap.get(this).setNumberOfTrips(numberOfTrips);
        if (numberOfTrips>=100) {
            sharedStatusMap.get(this).setElevatorStatus(ElevatorStatus.MAINTENANCE);
        }
        openDoor();
    }


    public int getElevatorId() {
        return elevatorId;
    }

    public boolean equals (Object object) {
        if(object instanceof Elevator && ((Elevator)object).getElevatorId() == this.getElevatorId()) {
            return true;
        } else {
            return false;
        }
    }
}
