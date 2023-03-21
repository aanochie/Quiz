package csc1035.project2;

import org.hibernate.Session;

import org.hibernate.query.Query;

import java.io.Serializable;
import java.util.*;

public class QuizIO {

    // Method to get topic from input
    public static String topic(){
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
        return topic;
    }

    // Method to get type from input
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
        return type;
    }


    // Method to get quiz length from input
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

    // Method to specify incorrect questions option
    public static int incorrect(){
        Scanner sc = new Scanner(System.in);
        // Incorrect questions option
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

    // Method to get title of quiz
    public static String quizTitle(){
        // Allows user to set quizTitle to anything they want
        // Gives the user functionality to retrieve a specific quiz by title(maybe)
        // quizTitle() returns list of quiz titles
        Scanner sc = new Scanner(System.in);
        QuizManager quizManager = new QuizManager();

        // Stores all quiz titles in the Quizzes table
        List<String> allTitles = quizManager.getQuizTitles();

        String title = "";
        boolean validTitle = false;
        // Only update boolean if there is a title with matching string
        // if there is a title with matching string then run loop.
        while(!validTitle){
            System.out.println("Enter quiz title: ");
            title = sc.nextLine();
            if(!allTitles.contains(title)){
                validTitle = true;
            }
        }
        return title;
    }

    // Method to specify quizLength when incorrect questions are selected
    public static int incorrectQuizLength(int incorrectQuestions){
        int quizLength;
        if(incorrectQuestions == 0){
            quizLength = quizLength();
        } else {
            // Set to 1 since it can not be null and quizLength is going to be ignored by Listing.randomQuestionsId()
            quizLength = 1;
        }
        return quizLength;
    }

    // Generates a quiz of random questions given the length of the quiz
    public static void randomQuiz(){
        int quizLength = quizLength();
        String title = quizTitle();
        Listing listing = new Listing();
        int[] randGeneratedQuestionsIdArr = listing.randomQuestionsId(quizLength);
        Quiz quiz = new Quiz(randGeneratedQuestionsIdArr, title);
        quiz.save();
    }

    // Generates a quiz of random incorrect questions
    public static void incorrectQuestionsQuiz(){
        Listing listing = new Listing();
        String title = quizTitle();
        int[] randomIncorrectQuestions = listing.randomQuestionsIdIncorrect();
        if(randomIncorrectQuestions.length == 0){
            System.out.println("There are no previously answered incorrect questions");
        } else {
            Quiz quiz = new Quiz(randomIncorrectQuestions, title);
            quiz.save();
        }
    }

    // Generates a quiz of random questions given topic, length and option for incorrect questions
    public static void specifiedQuizTopic(){
        Scanner sc = new Scanner(System.in);

        // Topic, incorrect questions option and quizLength selection
        String topic = topic();
        int incorrectQuestions = incorrect();
        int quizLength = incorrectQuizLength(incorrectQuestions);
        String title = quizTitle();

        // Quiz generator
        Listing listing = new Listing();
        int[] generatedQuestionsId = listing.randomQuestionsId(topic, incorrectQuestions, quizLength);
        Quiz quiz = new Quiz(generatedQuestionsId, title);
        quiz.save();

    }

    // Generates a qiz of random questions given type, length and option for incorrect questions
    public static void specifiedQuizType(){
        Scanner sc = new Scanner(System.in);

        //Type, incorrect option and quiz length
        int type = type();
        int incorrectQuestions = incorrect();
        int quizLength = incorrectQuizLength(incorrectQuestions);
        String title = quizTitle();

        // Quiz generator
        Listing listing = new Listing();
        int[] generatedQuestionsId = listing.randomQuestionsId(type, incorrectQuestions, quizLength);
        Quiz quiz = new Quiz(generatedQuestionsId, title);
        quiz.save();

    }

    // Generates a quiz of specified question type, topic, quizLength and option for incorrect questions
    public static void specifiedQuizAll() {
        Scanner sc = new Scanner(System.in);

        // Topic, type and incorrect questions option
        String topic = topic();
        int type = type();
        int incorrectQuestions = incorrect();
        int quizLength = incorrectQuizLength(incorrectQuestions);
        String title = quizTitle();

        // Quiz generator
        Listing listing = new Listing();
        int[] generatedQuestionsId = listing.randomQuestionsId(topic, type, incorrectQuestions, quizLength);
        Quiz quiz = new Quiz(generatedQuestionsId, title);
        quiz.save();

    }

    // Menu to give user option for which quiz to generate
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
                6. Exit.""");

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
                System.out.println("Exit.");
                break;
            }
            break;
        }
        switch (quizType){
            case 1 -> QuizIO.randomQuiz();
            case 2 -> QuizIO.incorrectQuestionsQuiz();
            case 3 -> QuizIO.specifiedQuizTopic();
            case 4 -> QuizIO.specifiedQuizType();
            case 5 -> QuizIO.specifiedQuizAll();
        }
    }

    public static void selectQuiz() {
        QuizManager qm = new QuizManager();

        List<String> titles = QuizManager.getQuizTitles();

        for (String title: titles) {
            System.out.println(title);
        }
        System.out.println("Which quiz would you like to take? Please enter its full title.");
        Scanner scanner = new Scanner(System.in);
        String chosenQuiz = scanner.nextLine();

        if (!titles.contains(chosenQuiz)) {
            return;
        }

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query quizQuery = session.createQuery("select q.id from Quiz q where " + "q.title= :title");
        quizQuery.setParameter("title", chosenQuiz);
        Quiz quiz = session.get(Quiz.class, (Serializable) quizQuery.list().get(0));
        session.getTransaction().commit();

        takeQuiz(quiz);
    }

    public static void takeQuiz(Quiz quiz) {
        Question question;
        Session session;
        int score = 0;

        // Ugly code but it works so ¯\_(ツ)_/¯
        ArrayList<Integer> questionIds = new ArrayList<>();
        questionIds.add(quiz.getQid_1());
        questionIds.add(quiz.getQid_2());
        questionIds.add(quiz.getQid_3());
        questionIds.add(quiz.getQid_4());
        questionIds.add(quiz.getQid_5());
        if (quiz.getLength() > 5) {
            questionIds.add(quiz.getQid_6());
            questionIds.add(quiz.getQid_7());
            questionIds.add(quiz.getQid_8());
            questionIds.add(quiz.getQid_9());
            questionIds.add(quiz.getQid_10());
        }
        if (quiz.getLength() > 10) {
            questionIds.add(quiz.getQid_11());
            questionIds.add(quiz.getQid_12());
            questionIds.add(quiz.getQid_13());
            questionIds.add(quiz.getQid_14());
            questionIds.add(quiz.getQid_15());
        }
        if (quiz.getLength() > 15) {
            questionIds.add(quiz.getQid_16());
            questionIds.add(quiz.getQid_17());
            questionIds.add(quiz.getQid_18());
            questionIds.add(quiz.getQid_19());
            questionIds.add(quiz.getQid_20());
        }

        int n = 1;
        for (Integer questionId : questionIds) {
            System.out.println("\nQuestion " + n + ":");
            n++;

            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            question = session.get(Question.class, questionId);
            session.getTransaction().commit();

            if (question.getType() == 1) {
                score += mcq(question);
            } else {
                score += saq(question);
            }
        }
        System.out.println("Score: " + score);
    }

    public static int mcq(Question question) {
        Scanner scanner = new Scanner(System.in);

        System.out.println(question.getQuestion());

        ArrayList<String> answers = question.getAnswers();
        Collections.shuffle(answers);

        List<String> validChoices = Arrays.asList("1", "2", "3", "4");

        System.out.println("1. " + answers.get(0) + "\n2. " + answers.get(1) + "\n3. " + answers.get(2) + "\n4. " + answers.get(3));
        String choice = scanner.nextLine();

        while (!validChoices.contains(choice)) {
            System.out.println("Invalid input.");
            choice = scanner.nextLine();
        }

        int choiceInt = Integer.parseInt(choice) - 1;

        if (Objects.equals(answers.get(choiceInt), question.getAnswer())) {
            question.setCorrect(2);
            return 1;
        } else {
            question.setCorrect(1);
            return 0;
        }
    }

    public static int saq(Question question) {
        Scanner scanner = new Scanner(System.in);

        System.out.println(question.getQuestion());

        String answer = question.getAnswer();

        String givenAnswer = scanner.nextLine().trim().toLowerCase();

        if (answer.toLowerCase().equals(givenAnswer)) {
            question.setCorrect(2);
            return 1;
        } else {
            question.setCorrect(1);
            return 0;
        }
    }
}

