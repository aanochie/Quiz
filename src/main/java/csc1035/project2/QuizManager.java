package csc1035.project2;

import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class QuizManager {

    // List of all quizzes in the database
    private static List allQuizzes = null;
    // List of all quiz titles in table
    private static List quizTitles = null;

    // Getters and Setters

    public static List getAllQuizzes(){
        return allQuizzes;
    }

    public static List getQuizTitles(){
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

    public static void main(String[] args){
        QuizManager quizManager = new QuizManager();
        System.out.println(getQuizTitles());
    }
}
