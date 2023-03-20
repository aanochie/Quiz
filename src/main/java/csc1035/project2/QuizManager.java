package csc1035.project2;

import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class QuizManager {

    // List of all quizzes in the database
    private final List<Quiz> allQuizzes;
    // List of all quiz titles in table
    private final List<String> quizTitles;

    // Getters and Setters

    public List<Quiz> getAllQuizzes(){
        return this.allQuizzes;
    }

    public List<String> getQuizTitles(){
        return this.quizTitles;
    }

    // Constructor
    // Sets allQuizzes and quizTitles list
    public QuizManager(){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query quizQuery = session.createQuery("from Quiz");
        this.allQuizzes = quizQuery.list();

        Query titleQuery = session.createQuery("select q.title from Quiz q");
        this.quizTitles = titleQuery.list();

    }

    public static void main(String[] args){
        QuizManager quizManager = new QuizManager();
        System.out.println(quizManager.getQuizTitles());
    }
}
