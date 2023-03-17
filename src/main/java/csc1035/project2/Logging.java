package csc1035.project2;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class Logging {

    public static void logResults() {

        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();

            Quiz Quiz = (session.get(Quiz.class, 95));

            Quiz.setScore(10);
            session.update(Quiz);
            session.getTransaction().commit();

        } catch (HibernateException e) {
            if (session != null) session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public static void main(String[] args) {
        Logging.logResults();
    }

}
