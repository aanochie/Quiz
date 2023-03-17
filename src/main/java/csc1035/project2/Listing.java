package csc1035.project2;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class Listing {
    private List<Question> allQuestions = new ArrayList<>();
    public Listing(){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("from Question ");
        this.allQuestions = query.list();
        session.getTransaction().commit();
        session.close();
    }


    public void addQuestion(Question question) {
        allQuestions.add(question);
        // Add the Question object to the List object
    }

    public List<Question> getAllQuestions() {
        return allQuestions;
        // Returns a List object that stores all Question objects
    }

    public List<Question> getQuestionsByType(int type) {
        List<Question> questionsByType = new ArrayList<>();
        // Create a new List object to store the specified type of Question object
        for (Question question : allQuestions) {
            if (question.getType() == type) {
                // If the type of the Question object is the same as the specified type
                questionsByType.add(question);
                // Add the Question object to the new List object
            }
        }
        return questionsByType;
        // Returns a List object that stores the Question object of the specified type
    }

    public List<Question> getIncorrectQuestions() {
        List<Question> incorrectQuestions = new ArrayList<>();
        // Create a new List object to store the specified type of Question object
        for (Question question : allQuestions) {
            if (question.getType() == 1) {
                // If the type of the Question object is the same as the specified type
                incorrectQuestions.add(question);
                // Add the Question object to the new List object
            }
        }
        return incorrectQuestions;
        // Returns a List object that stores the Question object of the specified type
    }

    public List<Question> getQuestionsByTopic(String topic) {
        List<Question> questionsByTopic = new ArrayList<>();
        // Create a new List object to store the Question object for the specified topic
        for (Question question : allQuestions) {
            if (question.getTopic().equals(topic)) {
                // If the topic of the Question object is the same as the specified topic
                questionsByTopic.add(question);
                // Add the Question object to the new List object
            }
        }
        return questionsByTopic;
        // Returns a List object that stores the Question object of the specified topic
    }

    public List<Integer> generatedQuestions(String topic, int type, int correct, int limit){
        List<Integer> generatedQuestionsId = new ArrayList<>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        Query query = session.createQuery("select q.id from Question q where type= :type " +
                "and topic= :topic and correct= :correct").setMaxResults(limit);
        query.setParameter("type", type);
        query.setParameter("topic", topic);
        query.setParameter("correct", correct);
        generatedQuestionsId = query.list();
        session.getTransaction().commit();
        session.close();
        
        /*
        for (Question q : getAllQuestions()){
            if(q.getTopic().equals(topic) && q.getType() == type && q.getCorrect() == correct){
                generatedQuestionsId.add(q.getId());
            }
        }
         */

        /*
        for (int i = 0; i < getAllQuestions().size(); i++) {
            if (getAllQuestions().get(i).getTopic().equals(topic) && getAllQuestions().get(i).getType() == type
                    && getAllQuestions().get(i).getCorrect() == correct) {
                generatedQuestionsId.add(getAllQuestions().get(i).getId());
            }
        }
        */
        
        return generatedQuestionsId;
    }

   public static void main(String[] args){
        Listing listing = new Listing();
        // listing.topicQuery("maths");
        // listing.typeQuery(2);
        // System.out.println(listing.generatedQuestions("maths", 1, 0, 10));
        // System.out.println(listing.getAllQuestions().get(0).getId());
        System.out.println(listing.allQuestions);
   }


}
