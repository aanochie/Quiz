package csc1035.project2;
import org.hibernate.Session;
import org.hibernate.query.Query;

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


    // Returns List of random question id given length of list and a list of Questions to randomise
    public static List<Integer> randomIndexList(int length, List<Question> questionList){
        // length refers to Quiz length
        // List to get index of random integers in the set
        List<Integer> randomIndexList = new ArrayList<>();
        // Set to store distinct random integers
        Set<Integer> randomIndexesSet = new HashSet<>();

        List<Integer> ranQuestionsIdList = new ArrayList<>();

        // Adds distinct random integers to randomIndex list and set given length.
        // Limit determines how many random numbers to generate
        while (randomIndexesSet.size() < length) {
            // Creates random number from 0 to amount of questions in Question table
            int rand = (int) (Math.random() * questionList.size());
            if (!randomIndexesSet.contains(rand)) {
                randomIndexesSet.add(rand);
                randomIndexList.add(rand);
            }
        }
        // Gets the question id from Questions using randomIndexList as the index
        for (int i = 0; i < length; i++) {
            Integer questionId = questionList.get(randomIndexList.get(i)).getId();
            ranQuestionsIdList.add(questionId);
        }
        return ranQuestionsIdList;
    }

    // Returns an array of questionsId given a list of Question
    public static int[] questionsToIdArray(List<Question> questions) {
        int length = questions.size();
        List<Integer> randomIndexList = randomIndexList(length, questions);
        return randomIndexList.stream().mapToInt(i -> i).toArray();
    }


    // Returns an array of questions id given topic and whether to use incorrect questions
    // Takes in account whether there are enough questions of the same topic
    // If there are not enough questions, return how many questions there are
    // This can be handled in QuizIO
    public int[] randomQuestionsId(String topic, int correct){
        List<Question> generatedQuestionsList;
        int[] generatedQuestionsIdArray;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        Query query = session.createQuery("from Question where " +
                "topic= :topic and correct= :correct");//.setMaxResults(limit);
        query.setParameter("topic", topic);
        query.setParameter("correct", correct);
        generatedQuestionsList = query.list();
        session.getTransaction().commit();
        session.close();

        generatedQuestionsIdArray = questionsToIdArray(generatedQuestionsList);

        return generatedQuestionsIdArray;
    }

    // Returns an array of questions id given type and whether to use incorrect questions
    // Takes in account whether there are enough questions of the same topic
    // If there are not enough questions, return how many questions there are
    // This can be handled in QuizIO
    public int[] randomQuestionsId(int type, int correct){
        List<Question> generatedQuestionsList;
        int[] generatedQuestionsIdArray;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        Query query = session.createQuery("from Question where type = :type " +
                "and correct = :correct ");
        query.setParameter("type", type);
        query.setParameter("correct", correct);
        generatedQuestionsList = query.list();
        session.getTransaction().commit();
        session.close();


        generatedQuestionsIdArray = questionsToIdArray(generatedQuestionsList);

        return generatedQuestionsIdArray;
    }

    // Returns array of random questions ids if incorrect questions are specified
    public int[] randomQuestionsIdIncorrect(int correct){
        List<Question> generatedQuestionsList = getIncorrectQuestions();
        return questionsToIdArray(generatedQuestionsList);
    }

    // Generates random distinct questions id given quiz length
    public int[] randomQuestionsId(int length) {
        // Set to store distinct questions
        List<Integer> ranQuestionsIdList = new ArrayList<>(randomIndexList(length, allQuestions));
        int[] ranQuestionsIdArr;

        // Converts generated list to array
        ranQuestionsIdArr = ranQuestionsIdList.stream().mapToInt(i -> i).toArray();
        return ranQuestionsIdArr;
    }

    // Returns Array of random questions id given topic, type, length, and
    // if to use incorrect questions
    // Need to check for amount of questions if incorrect and match for list
    // This can be done in QuizIO
    public int[] randomQuestionsId(String topic, int type, int correct, int length){
        List<Question> generatedQuestionsList = new ArrayList<>();
        int[] generatedQuestionsIdArr;

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        Query query = session.createQuery("from Question where type= :type " +
                "and topic= :topic and correct= :correct").setMaxResults(length);
        query.setParameter("type", type);
        query.setParameter("topic", topic);
        query.setParameter("correct", correct);
        // Stores query result to list of Questions
        generatedQuestionsList = query.list();
        session.getTransaction().commit();
        session.close();

        generatedQuestionsIdArr = randomIndexList(length, generatedQuestionsList).stream().mapToInt(i -> i).toArray();

        return generatedQuestionsIdArr;
    }

   public static void main(String[] args){
        Listing listing = new Listing();
        //listing.topicQuery("maths");
        //listing.typeQuery(2);
       //System.out.println(listing.generatedQuestions("maths", 1, 0, 3).size());
       //System.out.println(listing.getAllQuestions().get(0).getId());
       System.out.println(Arrays.toString(listing.randomQuestionsId("maths", 0)));
   }

}
