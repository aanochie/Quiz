package csc1035.project2;

import org.hibernate.Session;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class QuestionIO {
    public static void main() {
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
        Listing listing = new Listing();
        List<Question> allQuestions = listing.getAllQuestions();

        if (allQuestions.size() == 0) {
            return;
        }

        outputList(allQuestions);

        List<String> validChoices = Arrays.stream(IntStream.range(1, allQuestions.size() + 1).mapToObj(String::valueOf).toArray(String[]::new)).toList();

        Scanner myScanner = new Scanner(System.in);
        System.out.println("Which question would you like to delete? Please enter its number:");
        String questionNumber = myScanner.nextLine();

        while (!validChoices.contains(questionNumber)) {
            System.out.println("Invalid choice. Please enter a number from 1-" + allQuestions.size() + ":");
            questionNumber = myScanner.nextLine();
        }

        Question questionObj = allQuestions.get(Integer.parseInt(questionNumber) - 1);

        System.out.println(questionObj);
        System.out.println("Are you sure you want to delete this question?\n1. Yes\n2. No");

        String sure = myScanner.nextLine();
        while (!(sure.equals("1") || sure.equals("2"))) {
            System.out.println("Invalid choice. Please enter either 1 or 2:");
            sure = myScanner.nextLine();
        }

        if (sure.equals("1")) {
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(questionObj);
            session.getTransaction().commit();
        }
    }

    public static void listingIO() {
        Scanner myScanner = new Scanner(System.in);
        System.out.println("What would you like to list?\n1. All Questions\n2. Questions of a specific topic\n3. Questions of a specific type");
        String choice = myScanner.nextLine();

        List<String> validChoices = Arrays.asList("1", "2", "3");
        while (!validChoices.contains(choice)) {
            System.out.println("Invalid choice. Please enter either 1, 2 or 3:");
            choice = myScanner.nextLine();
        }

        List<Question> questions = null;
        switch (choice) {
            case "1" -> questions = getAllQuestions();
            case "2" -> questions = getQuestionsByTopic();
            case "3" -> questions = getQuestionsByType();
        }

        outputList(questions);
    }

    public static void outputList(List<Question> questionList) {
        for (int i = 0; i < questionList.size(); i++) {
            System.out.println((i + 1) + ". " + questionList.get(i) + "\n");
        }
    }

    public static List<Question> getAllQuestions() {
        Listing listing = new Listing();
        return listing.getAllQuestions();
    }

    public static List<Question> getQuestionsByTopic() {
        Scanner myScanner = new Scanner(System.in);
        System.out.println("Which topic would you like to see questions from?\n1. Architecture\n2. Maths\n3. Programming\n4. Databases");
        String choice = myScanner.nextLine();

        List<String> validChoices = Arrays.asList("1", "2", "3", "4");
        while (!validChoices.contains(choice)) {
            System.out.println("Invalid choice. Please enter either 1, 2, 3 or 4:");
            choice = myScanner.nextLine();
        }

        Listing listing = new Listing();
        List<Question> questions = null;
        switch (choice) {
            case "1" -> questions = listing.getQuestionsByTopic("architecture");
            case "2" -> questions = listing.getQuestionsByTopic("maths");
            case "3" -> questions = listing.getQuestionsByTopic("programming");
            case "4" -> questions = listing.getQuestionsByTopic("databases");
        }

        return questions;
    }

    public static List<Question> getQuestionsByType() {
        Scanner myScanner = new Scanner(System.in);
        System.out.println("Which type of questions would you like to see?\n1. Multiple choice\n2. Short answer");
        String choice = myScanner.nextLine();

        List<String> validChoices = Arrays.asList("1", "2");
        while (!validChoices.contains(choice)) {
            System.out.println("Invalid choice. Please enter either 1 or 2:");
            choice = myScanner.nextLine();
        }

        Listing listing = new Listing();
        List<Question> questions = null;
        switch (choice) {
            case "1" -> questions = listing.getQuestionsByType(1);
            case "2" -> questions = listing.getQuestionsByType(2);
        }

        return questions;
    }
}
