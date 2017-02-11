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

    public void startElevators() {

        int i = 1;

        while (i<=numberOfElevators) {

            ElevatorData elevatorData = new ElevatorData(i);

            Elevator elevator = new Elevator(i, numberOfFloors, elevatorData);

            runningElevatorMap.put(elevator, new ElevatorData(i));

            Thread elevatorThread = new Thread(elevator);

            elevatorThread.start();

            elevatorList.add(elevatorThread);

            i++;

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

            //System.out.println("Response from "+response.getElevatorId());

            //System.out.println(response.getResponseCode());

            //System.out.println(response.getFloorsAway());

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

