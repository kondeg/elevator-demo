package demo.kondeg.elevator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kdegtiarenko on 2/10/2017.
 */
public class ElevatorController {

    List<Elevator> elevatorList = new ArrayList<Elevator>();

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

            elevatorList.add(elevator);

            elevator.start();

        }



    }

    public void processRequest(int floor) throws ElevatorError {

        System.out.println("Processing request for floor "+floor);

        if (floor<1 || floor > numberOfFloors) {

            throw new ElevatorError("Invalid number of floors");

        } else {

            currentRequest = new Request(floor);

        }

    }
}
