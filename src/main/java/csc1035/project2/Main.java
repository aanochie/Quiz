package csc1035.project2;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;

public class Main {
    public static void main(String[] args) {
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);

        boolean running = true;
        while (running) {
            Scanner myScanner = new Scanner(System.in);
            System.out.println("What would you like to do?\n1. Create/Update/Delete a question\n2. List questions" +
                    "\n3. Create a quiz\n4. Delete a quiz\n5. Take a quiz\n6. Exit");

            String choice = myScanner.nextLine();

            List<String> validChoices = Arrays.asList("1", "2", "3", "4", "5", "6");

            while (!validChoices.contains(choice)) {
                System.out.println("Invalid choice. Please enter a number from 1-5:");
                choice = myScanner.nextLine();
            }

            switch (choice) {
                case "1" -> QuestionIO.main();
                case "2" -> QuestionIO.listingIO();
                case "3" -> GenerateQuiz.generateQuiz();
                case "4" -> DeleteQuiz.delete();
                case "5" -> TakeQuiz.selectQuiz();
                case "6" -> running = false;
            }
        }
    }
}
