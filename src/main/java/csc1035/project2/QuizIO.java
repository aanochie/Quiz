package csc1035.project2;

import java.util.*;

public class QuizIO {

    // Method to get quiz length
    public static int quizLength(){
        int quizLength;
        while (true) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter quiz length: 5, 10, 15, 20");
            String quizLengthInput = sc.nextLine();

            try {
                quizLength = Integer.parseInt(quizLengthInput);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Enter number of questions to be generated");
                continue;
            }
            List<Integer> validChoices = Arrays.asList(5, 10, 15, 20);
            if (!validChoices.contains(quizLength)) {
                System.out.println("Invalid input. Enter provided number of questions to be generated");
                continue;
            }
            break;
        }
        return quizLength;
    }

    // Generates a quiz of random questions given the length of the quiz
    public static void randomQuiz(){
        int quizLength = quizLength();
        Listing listing = new Listing();
        List<Integer> randGeneratedQuestionsIdList = new ArrayList<>(listing.randomQuestionsId(quizLength));
        Quiz quiz = new Quiz(randGeneratedQuestionsIdList);
        quiz.save();
    }

    // Generates a quiz of specified question type, topic, quizLength and option for incorrect questions
    public static void specifiedQuiz() {
        Scanner sc = new Scanner(System.in);

        // Topic option
        int topicChoice;
        String topic = "";
        while (true) {
            System.out.println("""
                    Topic:
                    1. Programming
                    2. Databases
                    3. Architecture
                    4. Maths
                    Enter topic number:""");
            String topicInput = sc.nextLine();
            try {
                topicChoice = Integer.parseInt(topicInput);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter topic number.");
                continue;
            }
            if (topicChoice < 1 || topicChoice > 4) {
                System.out.println("Invalid input. Please enter topic number.");
                continue;
            }
            break;
        }
        switch (topicChoice) {
            case 1 -> topic = "programming"; //Can be changed to fit table requirements
            case 2 -> topic = "databases";
            case 3 -> topic = "architecture";
            case 4 -> topic = "maths";
        }

        // Type selection
        int type;
        while (true) {
            System.out.println("""
                    Type:
                    1. Multiple choice
                    2. Short Answer
                    Enter type number:""");
            String typeInput = sc.nextLine();
            // Try block checks if integer is entered
            try {
                type = Integer.parseInt(typeInput);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Enter type number");
                continue;
            }
            if (type < 1 || type > 2) {
                System.out.println("Invalid input. Enter type number");
                continue;
            }
            break;
        }

        // Incorrect questions option
        System.out.println("Would you like to make the quiz with questions previously answered incorrectly?");
        System.out.println("Enter y/yes or any other key for No");
        String incorrectQuestionsChoice = sc.nextLine();
        int incorrectQuestions = 0;
        if (incorrectQuestionsChoice.equalsIgnoreCase("y") ||
                incorrectQuestionsChoice.equalsIgnoreCase("yes")) {
            incorrectQuestions = 1;
        }

        // Quiz length
        // This can be ignored if user selects incorrect questions and there aren't enough questions
        // Needs to be discussed if we would like to implement functionality
        int quizLength = quizLength();
        // From here generate quiz using Quiz class
        Listing listing = new Listing();
        listing.generatedQuestions(topic, type, incorrectQuestions, quizLength);
        List<Integer> generatedQuestionsId = new ArrayList<>(listing.generatedQuestions(topic, type, incorrectQuestions,
                quizLength));
        Quiz quiz = new Quiz(generatedQuestionsId);
        quiz.save();

    }

    // Method to get random specified quiz
    // Need to take topic

    public static void main(String[] args) {
        QuizIO.randomQuiz();
    }
}

