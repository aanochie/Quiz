package csc1035.project2;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            Scanner myScanner = new Scanner(System.in);
            System.out.println("What would you like to do?\n1. Create/Update/Delete a question.\n2. List questions.\n3. Create/Delete a quiz.\n4. Take a quiz.\n5. Exit");

            String choice = myScanner.nextLine();

            List<String> validChoices = Arrays.asList("1", "2", "3", "4", "5");

            while (!validChoices.contains(choice)) {
                System.out.println("Invalid choice. Please enter a number from 1-4:");
                choice = myScanner.nextLine();
            }

            switch (choice) {
                case "1" -> QuestionIO.main();
                case "5" -> running = false;
            }
        }
    }
}
