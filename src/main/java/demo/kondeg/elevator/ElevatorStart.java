package demo.kondeg.elevator;

import java.util.Scanner;

/**
 * Created by kdegtiarenko on 2/10/2017.
 */
public class ElevatorStart {

    public static void main(String args[]) {

        int numberOfFloors;

        int numberOfElevators;

        System.out.println("--- Welcome to Elevator Demo --");

        Scanner userInput = new Scanner(System.in);

        System.out.println("Please enter number of floors and press ENTER:");

        try {

            numberOfFloors = Integer.parseInt(userInput.next());

        } catch (NumberFormatException e) {

            System.out.println("Invalid Input: You did not enter a number. Exiting.");

            return;
        }

        System.out.println("Please enter number of elevators and press ENTER:");

        try {

            numberOfElevators = Integer.parseInt(userInput.next());

        } catch (NumberFormatException e) {

            System.out.println("Invalid Input: You did not enter a number. Exiting.");

            return;
        }

        System.out.println("--- Starting Elevator Controller --");

        ElevatorController controller = new ElevatorController(numberOfFloors, numberOfElevators);






        System.out.println("--- Finished Elevator Controller --");



    }



}
