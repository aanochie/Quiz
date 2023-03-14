package csc1035.project2;
import javax.persistence.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Listing {
    private List<Question> questions = new ArrayList<>();
    // Create a List object to store the Question object


    public void addQuestion(Question question) {
        questions.add(question);
        // Add the Question object to the List object
    }

    public List<Question> getAllQuestions() {
        return questions;
        // Returns a List object that stores all Question objects
    }

    public List<Question> getQuestionsByType(int type) {
        List<Question> questionsByType = new ArrayList<>();
        // Create a new List object to store the specified type of Question object

        for (Question question : questions) {
            if (question.getType() == type) {
                // If the type of the Question object is the same as the specified type
                questionsByType.add(question);
                // Add the Question object to the new List object
            }
        }

        return questionsByType;
        // Returns a List object that stores the Question object of the specified type
    }

    public List<Question> getQuestionsByTopic(String topic) {
        List<Question> questionsByTopic = new ArrayList<>();
        // Create a new List object to store the Question object for the specified topic

        for (Question question : questions) {
            if (question.getTopic().equals(topic)) {
                // If the topic of the Question object is the same as the specified topic
                questionsByTopic.add(question);
                // Add the Question object to the new List object
            }
        }

        return questionsByTopic;
        // Returns a List object that stores the Question object of the specified topic
    }


   public Object topicQuery (String topic) {
       // Need a list of question id or question object using topic Query
       // Query gets the question object and stores them into array
       // Query gives you resulting string
       // Returns Array of Question Object
       // Make Array into List of Question Object
       Session session = HibernateUtil.getSessionFactory().openSession();
       session.beginTransaction();

       //Query
       // Query returns a query of Question objects
       Query query = session.createQuery("from Question q where q.topic = :topic ");
       query.setParameter("topic", topic);

       Object results = query.getResultList(); // Returns list of arrays of Question object, to see array use ArraysToString()
       System.out.println(results);
       session.getTransaction().commit();

       return results;
   }

   public void typeQuery (int type){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        // Query
       Query query = session.createQuery("from Question q where q.type = :type");
       query.setParameter("type", type);

       Object results = query.getResultList();
       System.out.println(results);
       session.getTransaction().commit();
   }
   public static void main(String[] args){
        Listing listing = new Listing();
        //listing.topicQuery("databases");
        listing.typeQuery(2);
   }


}
