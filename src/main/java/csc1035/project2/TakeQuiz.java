package csc1035.project2;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.io.Serializable;
import java.util.*;
import java.util.stream.IntStream;

public class TakeQuiz {
    /**
     * allows the user to select a quiz to take
     */
    public static void selectQuiz() {
        QuizManager qm = new QuizManager();

        List<String> titles = qm.getQuizTitles();

        System.out.println("Quizzes:");
        for (int i = 1; i < titles.size() + 1; i++) {
            String title = titles.get(i - 1);
            System.out.println(i + ". " + title);
        }
        System.out.println("Which quiz would you like to take? Please enter its number.");
        Scanner scanner = new Scanner(System.in);

        List<String> validChoices = Arrays.stream(IntStream.range(1, titles.size() + 1).mapToObj(String::valueOf).toArray(String[]::new)).toList();
        String titleNumber = scanner.nextLine();

        while (!validChoices.contains(titleNumber)) {
            System.out.println("Invalid choice. Please enter a number from 1-" + titles.size() + ":");
            titleNumber = scanner.nextLine();
        }

        String chosenQuiz = titles.get(Integer.parseInt(titleNumber) - 1);

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query quizQuery = session.createQuery("select q.id from Quiz q where " + "q.title= :title");
        quizQuery.setParameter("title", chosenQuiz);
        Quiz quiz = session.get(Quiz.class, (Serializable) quizQuery.list().get(0));
        session.getTransaction().commit();

        takeQuiz(quiz);
    }

    /**
     * creates an ArrayList of the quiz's questions and then iterates through them, incrementing the score variable if the user gets a question right
     * @param quiz the quiz being taken
     */
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
            session.beginTransaction();
            question.update();
            session.getTransaction().commit();
        }
        System.out.println("Score: " + score + "/" + quiz.getLength());

        logResults(quiz, score);
    }

    /**
     * allows the user to take a multiple choice question by displaying the question and the 4 options, then asking for input
     * @param question the question being taken
     * @return 1 if the user got the question correct, 0 if not
     */
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

    /**
     * allows the user to take a short answer question by displaying the question, then asking for input
     * @param question the question being taken
     * @return 1 if the user got the question correct, 0 if not
     */
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

    /**
     * logs the user's score
     * @param quiz the quiz being taken
     * @param score the user's score
     */
    public static void logResults(Quiz quiz, int score) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();

            //sets the score of the quiz to what the user got
            quiz.setScore(score);
            session.update(quiz);       //updates the quiz score of the quiz
            session.getTransaction().commit();

        } catch (HibernateException e) {
            if (session != null) session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
