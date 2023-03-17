package csc1035.project2;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.*;

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

    public List<Question> getIncorrectQuestions() {
        List<Question> incorrectQuestions = new ArrayList<>();
        for (Question question : allQuestions) {
            if (question.getCorrect() == 1) {
                incorrectQuestions.add(question);
            }
        }
        return incorrectQuestions;
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

    public Set<Question> generatedQuestions(String topic, int type, int correct, int limit){
        List<Question> generatedQuestionsList = new ArrayList<>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        Query query = session.createQuery("from Question q where type= :type " +
                "and topic= :topic and correct= :correct").setMaxResults(limit);
        query.setParameter("type", type);
        query.setParameter("topic", topic);
        query.setParameter("correct", correct);
        generatedQuestionsList = query.list();
        session.getTransaction().commit();
        session.close();

        return new HashSet<>(generatedQuestionsList);
    }

    public Set<Question> randomQuestions(int limit){
        // Set to store distinct questions
        Set<Question> ranQuestionsSet = new HashSet<Question>();

        // List to get index of random integers in the set
        List<Integer> randomIndexList = new ArrayList<>();
        // Set to store distinct random integers
        Set<Integer> randomIndexesSet = new HashSet<>();

        // Adds distinct random integers to randomIndex list and set given limit.
        // Limit determines how many random numbers to generate
        while (randomIndexesSet.size() < limit){
            // Creates random number from 0 to amount of questions in Question table
            int rand = (int) (Math.random() * allQuestions.size());
            if(!randomIndexesSet.contains(rand)) {
                randomIndexesSet.add(rand);
                randomIndexList.add(rand);
            }
        }
        // Gets the question id from Questions using randomIndexList as the index
        for (int i=0; i < limit; i++){
            Question question = allQuestions.get(randomIndexList.get(i));
            ranQuestionsSet.add(question);
        }
        return ranQuestionsSet;
    }

   public static void main(String[] args){
        Listing listing = new Listing();
        //listing.topicQuery("maths");
        //listing.typeQuery(2);
       //System.out.println(listing.generatedQuestions("maths", 1, 0, 3).size());
       //System.out.println(listing.getAllQuestions().get(0).getId());
       System.out.println(listing.randomQuestions(10));
   }


}
