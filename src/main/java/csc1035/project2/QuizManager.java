package csc1035.project2;

import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

/**
 * Class to manage all the quizzes in the Quizzes table
 * @author A.Anochie
 */
public class QuizManager {


    /**
     * List of all quizzes in the table
     */
    private static List<Quiz> allQuizzes;

    /**
     *  List of all quiz titles in table
     */
    private static List<String> quizTitles;

    public static List<Quiz> getAllQuizzes(){
        return allQuizzes;
    }

    public List<String> getQuizTitles(){
        return quizTitles;
    }


    /**
     * Constructor sets allQuizzes and quizTitles titles from the Quizzes table.
     */
    public QuizManager(){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query quizQuery = session.createQuery("from Quiz");
        allQuizzes = quizQuery.list();

        Query titleQuery = session.createQuery("select q.title from Quiz q");
        quizTitles = titleQuery.list();

        session.getTransaction().commit();
        session.close();

    }
}
