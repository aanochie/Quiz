package csc1035.project2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class QuizIO {
    public static void run(){
        Scanner sc = new Scanner(System.in);

        // Topic option
        int topicChoice;
        String topic = "";
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
        int incorrectQuestions = 0;
        if(incorrectQuestionsChoice.equalsIgnoreCase("y") ||
                incorrectQuestionsChoice.equalsIgnoreCase("yes")){
            incorrectQuestions = 1;
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
        List<Integer> generatedQuestionsId = new ArrayList<>();
        Listing listing = new Listing();
        System.out.println(listing.generatedQuestions(topic, type, incorrectQuestions, 1));
        Quiz quiz = new Quiz(generatedQuestionsId);
        quiz.save();

    }
    public static void main(String[] args){
        QuizIO.run();
    }
    // Generate quiz
    /*
    *Create random list method
    * should take a list of all questions and return a list of random questions
    * list size should be the same size as the quizLength
    * for loop to iterate through list randomly could make the list a set.
    * Set allows for non-repeating qIds.
    * for(i=0; i<limit; i++){
    *   Set.add(random.nextInt(allQuestions.size()-1)
    * }
    * Check theme settings so you know what theme to apply to intellij
    * What if random nextInt gets a number that is already in the Set
    * Could generate a set of random ints which are in the bounds of
    * the allQuestions list. Store this in an array
    * for(i=0, j=0; i<limit && j<limit; i++, j++){
    *   Set.add(allQuestions.get(randArray.get(j))
    * }
    *
    * */
}
