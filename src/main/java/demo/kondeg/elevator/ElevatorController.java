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

    private ElevatorController(){};

    public ElevatorController(int numberOfFloors, int numberOfElevators) {

        this.numberOfFloors = numberOfFloors;

        this.numberOfElevators = numberOfElevators;

    }

    public void startElevators() {

        int i = 1;

        while (i<=numberOfElevators) {

            Elevator elevator = new Elevator(i, numberOfFloors);

            elevatorList.add(elevator);


        }

    }
}
