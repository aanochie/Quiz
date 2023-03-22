package csc1035.project2;

import org.hibernate.Session;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class QuestionIO {
    /**
     * provides an interface which lets users decide whether they want to create, update or delete a question
     */
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

    /**
     * asks the user for information to create a question
     */
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

    /**
     * displays every question and then makes the user choose one
     * @param action a string which is either 'update' or 'delete'
     * @return the question chosen by the user
     */
    public static Question getQuestion(String action) {
        Listing listing = new Listing();
        List<Question> allQuestions = listing.getAllQuestions();

        if (allQuestions.size() == 0) {
            return null;
        }

        outputList(allQuestions);

        List<String> validChoices = Arrays.stream(IntStream.range(1, allQuestions.size() + 1).mapToObj(String::valueOf).toArray(String[]::new)).toList();

        Scanner myScanner = new Scanner(System.in);
        System.out.println("Which question would you like to " + action + "? Please enter its number:");
        String questionNumber = myScanner.nextLine();

        while (!validChoices.contains(questionNumber)) {
            System.out.println("Invalid choice. Please enter a number from 1-" + allQuestions.size() + ":");
            questionNumber = myScanner.nextLine();
        }

        return allQuestions.get(Integer.parseInt(questionNumber) - 1);
    }

    /**
     * allows the user to choose a part of the question to update
     */
    public static void update() {
        Scanner myScanner = new Scanner(System.in);

        Question questionObj = getQuestion("update");

        if (questionObj == null) {
            return;
        }

        System.out.println("Which part would you like to update?\n1. Question\n2. Answer");
        String choice = myScanner.nextLine();
        while (!(choice.equals("1") || choice.equals("2"))) {
            System.out.println("Invalid choice. Please enter either 1 or 2:");
            choice = myScanner.nextLine();
        }

        switch (choice) {
            case "1" -> updateQuestion(questionObj);
            case "2" -> updateAnswer(questionObj);
        }

        System.out.println("Updated question:\n" + questionObj);
    }

    /**
     * allows the user to enter the new question
     * @param questionObj the question being updated
     */
    public static void updateQuestion(Question questionObj) {
        Scanner myScanner = new Scanner(System.in);

        System.out.println("Old question: " + questionObj.getQuestion());

        System.out.println("Please enter the updated question:");
        String newQuestion = myScanner.nextLine();

        questionObj.setQuestion(newQuestion);
        questionObj.update();
    }

    /**
     * allows the user to enter the new answer
     * @param questionObj the question being updated
     */
    public static void updateAnswer(Question questionObj) {
        Scanner myScanner = new Scanner(System.in);

        System.out.println("Old answer: " + questionObj.getAnswer());

        System.out.println("Please enter the updated answer:");
        String newAnswer = myScanner.nextLine();

        questionObj.setAnswer(newAnswer);
        questionObj.update();
    }

    /**
     * deletes a question that the user chooses
     */
    public static void delete() {
        Scanner myScanner = new Scanner(System.in);

        Question questionObj = getQuestion("delete");

        if (questionObj == null) {
            return;
        }

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

    /**
     * allows the user to choose criteria for questions to be listed
     */
    public static void listingIO() {
        boolean running = true;
        while (running) {
            Scanner myScanner = new Scanner(System.in);
            System.out.println("What would you like to list?\n1. All Questions\n2. Questions of a specific topic\n3. Questions of a specific type\n4. Questions answered incorrectly\n5. Back");
            String choice = myScanner.nextLine();

            List<String> validChoices = Arrays.asList("1", "2", "3", "4", "5");
            while (!validChoices.contains(choice)) {
                System.out.println("Invalid choice. Please enter either 1, 2, 3, 4 or 5:");
                choice = myScanner.nextLine();
            }

            List<Question> questions = null;
            switch (choice) {
                case "1" -> questions = getAllQuestions();
                case "2" -> questions = getQuestionsByTopic();
                case "3" -> questions = getQuestionsByType();
                case "4" -> questions = getIncorrectQuestions();
                case "5" -> running = false;
            }

            if (!choice.equals("5")) {
                outputList(questions);
            }
        }
    }

    /**
     * @param questionList the list of questions to be outputted
     */
    public static void outputList(List<Question> questionList) {
        for (int i = 0; i < questionList.size(); i++) {
            System.out.println((i + 1) + ". " + questionList.get(i) + "\n");
        }
    }

    /**
     * @return a list containing every question
     */
    public static List<Question> getAllQuestions() {
        Listing listing = new Listing();
        return listing.getAllQuestions();
    }

    /**
     * @return a list containing every question of a specified topic
     */
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

    /**
     * @return a list containing every question that the user answered incorrectly the last time it was attempted
     */
    public static List<Question> getIncorrectQuestions() {
        Listing listing = new Listing();
        return listing.getIncorrectQuestions();
    }

    /**
     * @return a list containing every question of a specified type
     */
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
