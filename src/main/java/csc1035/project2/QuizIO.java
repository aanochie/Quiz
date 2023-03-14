package csc1035.project2;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class QuizIO {
    public static void main(){
        Scanner sc = new Scanner(System.in);

        // Topic option
        int topicChoice;
        while(true) {
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
            if(topicChoice < 1 || topicChoice > 4){
                System.out.println("Invalid input. Please enter topic number.");
                continue;
            }
            break;
        }
        String topic;
        switch(topicChoice) {
            case 1 -> topic = "programming"; //Can be changed to fit table requirements
            case 2 -> topic = "databases";
            case 3 -> topic = "architecture";
            case 4 -> topic = "maths";
        }

        // Type selection
        int type;
        while(true) {
            System.out.println("""
                    Type:
                    1. Multiple choice
                    2. Short Answer
                    Enter type number:""");
            String typeInput = sc.nextLine();
            // Try block checks if integer is entered
            try{
                type = Integer.parseInt(typeInput);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Enter type number");
                continue;
            }
            if(type < 1 || type > 2){
                System.out.println("Invalid input. Enter type number");
                continue;
            }
            break;
        }

        // Incorrect questions option
        System.out.println("Would you like to make the quiz with questions previously answered incorrectly?");
        System.out.println("Enter y/yes or any other key for No");
        String incorrectQuestionsChoice = sc.nextLine();
        boolean incorrectQuestions;
        if(incorrectQuestionsChoice.equalsIgnoreCase("y") ||
                incorrectQuestionsChoice.equalsIgnoreCase("yes")){
            incorrectQuestions = true;
        }

        // Quiz length
        // This can be ignored if user selects incorrect questions and there aren't enough questions
        // Needs to be discussed if we would like to implement functionality
        int quizLength;
        while (true) {
            System.out.println("Enter quiz length: 5, 10, 15, 20");
            String quizLengthInput = sc.nextLine();

            try {
                quizLength = Integer.parseInt(quizLengthInput);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Enter number of questions to be generated");
                continue;
            }
            List<Integer> validChoices = Arrays.asList(5, 10, 15, 20);
            if(!validChoices.contains(quizLength)){
                System.out.println("Invalid input. Enter provided number of questions to be generated");
                continue;
            }
            break;
        }
        // From here generate quiz using Quiz class

    }
    // Generate quiz
    /*
    * Need a list of generated question ids
    * Take each id and create a quiz object
    * In quiz class should have constructor which takes a list of ids(int) as a parameter
    * For each id in the list map to respective qid_n
    * generatedQuestionId
    * Quiz(List<Integer> generatedQuestionId)
    * Needs to get questions by type, topic and incorrect
    * For length take the list generated, mysql query should include LIMIT 5/10/
    * */
    public static void generatingQuiz(){

    }
}
