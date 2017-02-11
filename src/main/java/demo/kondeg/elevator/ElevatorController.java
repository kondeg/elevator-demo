package demo.kondeg.elevator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by kdegtiarenko on 2/10/2017.
 */
public class ElevatorController {

    List<Thread> elevatorList = new ArrayList<Thread>();

    private Map<Elevator, ElevatorData> runningElevatorMap = new ConcurrentHashMap<Elevator, ElevatorData>();

    int numberOfFloors;

    int numberOfElevators;

    Request currentRequest;

    private ElevatorController(){};

    public ElevatorController(int numberOfFloors, int numberOfElevators) {

        this.numberOfFloors = numberOfFloors;

        this.numberOfElevators = numberOfElevators;

    }

    /**
     * Start the elevator threads. It does not quite work, run out of time. But the idea is that each elevator runs asynchronously in its own thread monitoring
     * its queue. It picks up a request from its queue. Communication between elevator and parent thread is through a shared map which should be thread
     * safe implementation of the map
     *
     * Note: not finished, run out of time
     *
     */
    public void startElevators() {

        int i = 1;

        while (i<=numberOfElevators) {

            Elevator elevator = new Elevator(i, numberOfFloors, runningElevatorMap);

            runningElevatorMap.put(elevator, new ElevatorData(i));

            i++;



        }

        for (Elevator elevator: runningElevatorMap.keySet()) {

            Thread thread = new Thread(elevator);

            thread.start();

        }



        for (Thread thread : elevatorList) {

            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }



    }

    public void stopElevators() {

        for (Elevator elevator: runningElevatorMap.keySet()) {

            runningElevatorMap.get(elevator).setElevatorStatus(ElevatorStatus.MAINTENANCE);


        }

    }

    /**
     * Go throught the shared elevator map. Pick a preferred elevator based on current status, add request to the queue of preferred elevator.
     * The idea is that the elevator should pick up the request from its queue asynchronously. Each elevator has its own object in the map
     * that contains its current status. It also has a strategy for calculating a response to a requested floor. This method analyzes the responses
     * to pick the best candidate
     *
     * Note: not finished, run out of time.
     *
     * @param floor
     * @throws ElevatorError
     */
    public void processRequest(int floor) throws ElevatorError {

        System.out.println("Processing request for floor "+floor);

        if (floor<1 || floor > numberOfFloors) {

            throw new ElevatorError("Invalid number of floors");

        } else {

            currentRequest = new Request(floor);

        }

        ElevatorData preferedElevator=null;

        Response.ResponseCode currentResponseCode = null;

        int currentIdleFloorDifference= numberOfFloors;

        Set<Elevator> elevatorSet = runningElevatorMap.keySet();

        System.out.println("Elevator size is "+elevatorSet.size());

        for (Elevator elevator : elevatorSet) {

            Response response =  runningElevatorMap.get(elevator).processRequest(currentRequest);


            if (response.getResponseCode() == Response.ResponseCode.IDLE_SAME_FLOOR) {

                preferedElevator = runningElevatorMap.get(elevator);

                currentResponseCode = Response.ResponseCode.IDLE_SAME_FLOOR;

            } else if (response.getResponseCode() == Response.ResponseCode.PASSING) {

                if(currentResponseCode != Response.ResponseCode.IDLE_SAME_FLOOR) {

                    preferedElevator = runningElevatorMap.get(elevator);

                    currentResponseCode = Response.ResponseCode.PASSING;

                }

            } else if (response.getResponseCode() == Response.ResponseCode.IDLE) {

                System.out.println("Response code "+Response.ResponseCode.IDLE);

                if (currentResponseCode!=Response.ResponseCode.IDLE_SAME_FLOOR && currentResponseCode!=Response.ResponseCode.PASSING) {

                    //System.out.println("Current Response Code "+currentResponseCode);

                    if (currentIdleFloorDifference>response.getFloorsAway()) {

                        preferedElevator = runningElevatorMap.get(elevator);

                        currentResponseCode = Response.ResponseCode.IDLE;

                        currentIdleFloorDifference = response.getFloorsAway();
                    }

                }

            }

        }

        System.out.println("Preferred Elevator "+preferedElevator.getElevatorId());

        System.out.println("Current response code "+currentResponseCode);

        System.out.println("Current Idle floor difference "+currentIdleFloorDifference);


        //Add to request to preferred elevator queue. The idea is that the elevator will pick up its request from the queue
        if (preferedElevator!=null) {

            System.out.println("Adding request to "+preferedElevator.getElevatorId());

            preferedElevator.addToQueue(currentRequest);

        }

    }

    }

