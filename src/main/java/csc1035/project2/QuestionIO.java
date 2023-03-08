package csc1035.project2;

import org.hibernate.Session;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class QuestionIO {
    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            Scanner myScanner = new Scanner(System.in);
            System.out.println("What would you like to do?\n1. Create a question.\n2. Update a question.\n3. Delete a question.\n4. Back.");

            String choice = myScanner.nextLine();

            List<String> validChoices = Arrays.asList("1", "2", "3", "4");

            while (!validChoices.contains(choice)) {
                System.out.println("Invalid choice. Please enter a number from 1-4:");
                choice = myScanner.nextLine();
            }

            switch (choice) {
                case "1" -> create();
                case "2" -> update();
                case "3" -> delete();
                case "4" -> running = false;
            }
        }
    }

    public static void create() {
        Scanner myScanner = new Scanner(System.in);
        System.out.println("Please enter the question:");
        String question = myScanner.nextLine();

        System.out.println("Please enter the question topic:");
        String topic = myScanner.nextLine();

        System.out.println("Please enter the question type:\n1. Multiple Choice\n2. Short Answer");
        String type = myScanner.nextLine();
        List<String> validTypes = Arrays.asList("1", "2");
        while (!validTypes.contains(type)) {
            System.out.println("Invalid choice. Please enter either 1 or 2:");
            type = myScanner.nextLine();
        }

        System.out.println("Please enter the correct answer:");
        String answer = myScanner.nextLine();

        int typeInt = Integer.parseInt(type);
        ArrayList<String> otherAnswers = new ArrayList<>();
        Question questionObj;
        if (typeInt == 1) {
            for (int i = 0; i < 3; i++) {
                System.out.println("Please enter an incorrect answer:");
                otherAnswers.add(myScanner.nextLine());
            }
            questionObj = new Question(question, topic, typeInt, answer, otherAnswers);

        } else {
            questionObj = new Question(question, topic, typeInt, answer);
        }

        System.out.println(questionObj);
        System.out.println("Are you sure you want to add this question?\n1. Yes\n2. No");

        String sure = myScanner.nextLine();
        while (!validTypes.contains(sure)) {
            System.out.println("Invalid choice. Please enter either 1 or 2:");
            sure = myScanner.nextLine();
        }

        if (sure.equals("1")) {
            questionObj.save();
        }
    }

    public static void update() {

    }

    public static void delete() {
        
    }
}
