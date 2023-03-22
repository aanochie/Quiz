package csc1035.project2;

import java.util.*;

/**
 * This class generates a specified quiz taking user input for the required parameters to generate quiz
 * @author A. Anochie
 */
public class GenerateQuiz {

    /**
     *
     * @return returns string containing user selected topic for quiz generation
     */
    public static String topic(){
        Scanner sc = new Scanner(System.in);
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
            // Try block check if integer is inputted
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
            case 1 -> topic = "programming";
            case 2 -> topic = "databases";
            case 3 -> topic = "architecture";
            case 4 -> topic = "maths";
        }
        return topic;
    }

    /**
     *
     * @return returns integer which specifies the question type for quiz generation.
     */
    public static int type(){
        Scanner sc = new Scanner(System.in);
        int type;
        while (true) {
            System.out.println("""
                    Type:
                    1. Multiple choice
                    2. Short Answer
                    Enter type number:""");
            String typeInput = sc.nextLine();
            // Try block checks if integer is inputted
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
        return type;
    }


    /**
     *
     * @return returns integer of for quiz length specified by user selection.
     */
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

    /**
     *
     * @return returns integer to specify previously answered incorrect questions selection
     */
    public static int incorrect(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Would you like to make the quiz with questions previously answered incorrectly?");
        System.out.println("Enter y/yes or any other key for No");
        String incorrectQuestionsChoice = sc.nextLine();
        int incorrectQuestions = 0;
        if (incorrectQuestionsChoice.equalsIgnoreCase("y") ||
                incorrectQuestionsChoice.equalsIgnoreCase("yes")) {
            incorrectQuestions = 1;
        }
        return incorrectQuestions;
    }

    /**
     *
     * @return returns string containing user selected quiz title.
     */
    public static String quizTitle(){
        Scanner sc = new Scanner(System.in);
        QuizManager quizManager = new QuizManager();

        List<String> allTitles = quizManager.getQuizTitles();

        String title = "";
        boolean validTitle = false;
        while(!validTitle){
            System.out.println("Enter quiz title: ");
            title = sc.nextLine();
            if(!allTitles.contains(title)){
                validTitle = true;
            } else {
                System.out.println("Quiz title already in use. Use different title.");
            }
        }
        return title;
    }

    /**
     *
     * @param incorrectQuestions defines whether user selected to make quiz out of previously
     *                           answered incorrect questions
     * @return returns quiz length when user selects to make quiz out of previously answered incorrect questions
     */
    public static int incorrectQuizLength(int incorrectQuestions){
        int incorrectQuizLength;
        if(incorrectQuestions == 0){
            incorrectQuizLength = quizLength();
        } else {
            // Set to 1 since it can not be null and quizLength is going to be ignored by Listing.randomQuestionsId()
            incorrectQuizLength = 1;
        }
        return incorrectQuizLength;
    }


    /**
     * Generates a quiz made out of random questions giving user option to select length of the quiz.
     */
    public static void randomQuiz(){
        int quizLength = quizLength();
        String title = quizTitle();
        Listing listing = new Listing();
        int[] randGeneratedQuestionsIdArr = listing.randomQuestionsId(quizLength);
        Quiz quiz = new Quiz(randGeneratedQuestionsIdArr, title);
    }


    /**
     * Generates a quiz made of random previously answered incorrectly questions
     */
    public static void incorrectQuestionsQuiz(){
        Listing listing = new Listing();
        String title = quizTitle();
        int[] randomIncorrectQuestions = listing.randomQuestionsIdIncorrect();
        if(randomIncorrectQuestions.length == 0){
            System.out.println("There are no previously answered incorrect questions");
        } else {
            Quiz quiz = new Quiz(randomIncorrectQuestions, title);
        }
    }

    /**
     *  Generates a quiz of random questions given topic, length and option for incorrect questions
     */
    public static void specifiedQuizTopic(){
        Scanner sc = new Scanner(System.in);

        // Topic, incorrect questions option, quizLength and title selection
        String topic = topic();
        int incorrectQuestions = incorrect();
        int quizLength = incorrectQuizLength(incorrectQuestions);
        String title = quizTitle();

        // Quiz generator
        Listing listing = new Listing();
        int[] generatedQuestionsId = listing.randomQuestionsId(topic, incorrectQuestions, quizLength);
        Quiz quiz = new Quiz(generatedQuestionsId, title);


    }

    /**
     * Generates a quiz of random questions given type, length and option for previously answered incorrect questions
      */
    public static void specifiedQuizType(){
        Scanner sc = new Scanner(System.in);

        //Type, incorrect option, quiz length and title selection
        int type = type();
        int incorrectQuestions = incorrect();
        int quizLength = incorrectQuizLength(incorrectQuestions);
        String title = quizTitle();

        // Quiz generator
        Listing listing = new Listing();
        int[] generatedQuestionsId = listing.randomQuestionsId(type, incorrectQuestions, quizLength);
        Quiz quiz = new Quiz(generatedQuestionsId, title);


    }

    /**
     * Generates a quiz of specified question type, topic, quizLength and option for previously answered
     * incorrect questions
      */
    public static void specifiedQuizAll() {
        Scanner sc = new Scanner(System.in);

        // Topic, type, incorrect questions and title option
        String topic = topic();
        int type = type();
        int incorrectQuestions = incorrect();
        int quizLength = incorrectQuizLength(incorrectQuestions);
        String title = quizTitle();

        // Quiz generator
        Listing listing = new Listing();
        int[] generatedQuestionsId = listing.randomQuestionsId(topic, type, incorrectQuestions, quizLength);
        Quiz quiz = new Quiz(generatedQuestionsId, title);


    }

    /**
     * Menu to give user option for which quiz to generate
      */
    public static void generateQuiz(){
        Scanner sc = new Scanner(System.in);
        int quizType;
        while(true){
            System.out.println("""
                1. Generate random quiz.
                2. Generate random quiz with all incorrect questions.
                3. Generate quiz with specified topic.
                4. Generate quiz with specified type.
                5. Generate quiz with specified topic, type and/or incorrect questions.
                6. Back.""");

            String inputChoice = sc.nextLine();
            try {
                quizType = Integer.parseInt(inputChoice);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Enter type number");
                continue;
            }
            if (quizType < 1 || quizType > 6) {
                System.out.println("Invalid input. Enter type number");
                continue;
            }
            if (quizType == 6){
                break;
            }
            break;
        }
        switch (quizType){
            case 1 -> randomQuiz();
            case 2 -> incorrectQuestionsQuiz();
            case 3 -> specifiedQuizTopic();
            case 4 -> specifiedQuizType();
            case 5 -> specifiedQuizAll();
        }
    }
}
