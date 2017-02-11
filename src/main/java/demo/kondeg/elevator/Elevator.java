package demo.kondeg.elevator;

/**
 * Created by kdegtiarenko on 2/10/2017.
 */
public class Elevator  {

    private final int minFloor = 1;

    private int maxFloor;

    private ElevatorData elevatorData;

    private int elevatorId;

    Request currentRequest = null;


    private Elevator(){}

    public Elevator(int elevatorId, int maxFloor, ElevatorData elevatorData) {

        this.elevatorId = elevatorId;

        this.maxFloor = maxFloor;

        this.elevatorData = elevatorData;
    }

    public void start() {

        System.out.println("Starting elevator "+getElevatorId());

        while (!elevatorData.getElevatorStatus().equals(ElevatorStatus.MAINTENANCE)) {

            if (!elevatorData.isQueueEmpty()) {

                currentRequest = elevatorData.removeFromQueue();

                System.out.println("Dispatching elevator "+getElevatorId()+" to "+currentRequest.getFloorRequested());

                closeDoor();

                try {

                    goToFloor(currentRequest.getFloorRequested());

                } catch (ElevatorError e) {
                    //DO some error handling
                    e.printStackTrace();
                }

                openDoor();


                elevatorData.setElevatorStatus(ElevatorStatus.IDLE);

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
        if (elevatorData.getCurrentPosition()>floor) {
            elevatorData.setElevatorStatus(ElevatorStatus.MOVING_DOWN);
        } else if (elevatorData.getCurrentPosition()<floor) {
            elevatorData.setElevatorStatus(ElevatorStatus.MOVING_UP);
        }

        while(floor!=elevatorData.getCurrentPosition()) {

            if(elevatorData.getElevatorStatus().equals(ElevatorStatus.MOVING_UP)) {
                elevatorData.setCurrentPosition(elevatorData.getCurrentPosition()+1);
                elevatorData.setNumberOfFloorTraveled(elevatorData.getNumberOfFloorTraveled()+1);
                System.out.println("Elevator "+getElevatorId()+" Position: "+elevatorData.getCurrentPosition()+" Destination: "+floor+" Number Of Floors Traveled: "+elevatorData.getNumberOfFloorTraveled());

            } else if(elevatorData.getElevatorStatus().equals(ElevatorStatus.MOVING_DOWN)) {
                elevatorData.setCurrentPosition(elevatorData.getCurrentPosition()-1);
                elevatorData.setNumberOfFloorTraveled(elevatorData.getNumberOfFloorTraveled()+1);
                System.out.println("Elevator "+getElevatorId()+" Position: "+elevatorData.getCurrentPosition()+" Destination: "+floor+" Number Of Floors Traveled: "+elevatorData.getNumberOfFloorTraveled());
            }

        }

        int numberOfTrips = elevatorData.getNumberOfTrips()+1;
        elevatorData.setNumberOfTrips(numberOfTrips);
        if (numberOfTrips>=100) {
            elevatorData.setElevatorStatus(ElevatorStatus.MAINTENANCE);
        }
        openDoor();
    }


    public int getElevatorId() {
        return elevatorId;
    }

}
