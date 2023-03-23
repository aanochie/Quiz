package csc1035.project2;

import org.hibernate.Session;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class DeleteQuiz {
    public static void quizList(){
        QuizManager quizManager = new QuizManager();
        List<Quiz> quizzes = quizManager.getAllQuizzes();
        for(int i=0; i < quizzes.size(); i++){
            System.out.println((i + 1) + ". " + quizzes.get(i).getTitle());
        }
    }

    public static Quiz getQuiz(){
        Scanner scanner = new Scanner(System.in);
        QuizManager quizManager = new QuizManager();
        List<Quiz> allQuizzes = quizManager.getAllQuizzes();
        if ( allQuizzes.size() == 0){
            return null;
        }
        quizList();

        List<String> validChoices =
                Arrays.stream(IntStream.range(1, allQuizzes.size() + 1).mapToObj(String::valueOf).toArray(String[]::new)).toList();

        System.out.println("Which quiz would you like to delete? Please enter its number: ");
        String quizNumber = scanner.nextLine();

        while(!validChoices.contains(quizNumber)) {
            System.out.println("Invalid choice. Please enter a number from 1-" + allQuizzes.size() + ":");
            quizNumber = scanner.nextLine();
        }

        return allQuizzes.get(Integer.parseInt(quizNumber) - 1);
    }

    public static void delete() {
        Scanner scanner = new Scanner(System.in);

        Quiz quizToDelete = getQuiz();
        if (quizToDelete == null){
            return;
        }

        System.out.println(quizToDelete.getTitle());
        System.out.println("Are sure you want to delete this quiz?\n1. Yes\n2. No");

        String confirmation = scanner.nextLine();
        while(!(confirmation.equals("1") || confirmation.equals("2"))){
            System.out.println("Invalid choice. Please enter either 1 for Yes or 2 for No");
            confirmation = scanner.nextLine();
        }

        if(confirmation.equals("1")) {
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(quizToDelete);
            session.getTransaction().commit();
            session.close();

        }
    }
}
