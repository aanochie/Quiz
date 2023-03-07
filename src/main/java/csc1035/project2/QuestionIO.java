package csc1035.project2;

import org.hibernate.Session;

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
                System.out.println("Invalid choice.");
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
        /*Question q = new Question("What is the capital of the UK?", "geography", 1, "London", "Paris", "Madrid", "Cardiff");

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(q);
        session.getTransaction().commit();*/
    }

    public static void update();
}
