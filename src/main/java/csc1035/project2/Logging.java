package csc1035.project2;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class Logging {

    //'logResults' method is used to log and store results obtained for a quiz
    public static void logResults() {

        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();

            //Selects the row of the quiz which has the quiz ID
            Quiz quiz = (session.get(Quiz.class, 95));

            //sets the score of the quiz for whatever the score that the user obtained
            quiz.setScore(10);
            session.update(quiz);       //updates the quiz score of the quiz
            session.getTransaction().commit();

        } catch (HibernateException e) {
            if (session != null) session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public static void main(String[] args) {
        //calls the method 'logResults'
        Logging.logResults();
    }

}
