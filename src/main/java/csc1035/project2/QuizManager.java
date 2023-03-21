package csc1035.project2;

import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class QuizManager {

    // List of all quizzes in the database
    private static List<Quiz> allQuizzes;
    // List of all quiz titles in table
    private static List<String> quizTitles;

    // Getters and Setters

    public static List<Quiz> getAllQuizzes(){
        return allQuizzes;
    }

    public static List<String> getQuizTitles(){
        return quizTitles;
    }

    // Constructor
    // Sets allQuizzes and quizTitles list
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
