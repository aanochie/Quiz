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

    /**
     *
     * @return returns a list of all Question objects in the Questions table
     */
    public List<Question> getAllQuestions() {
        return allQuestions;
        // Returns a List object that stores all Question objects
    }


    /**
     *
     * @return returns a list of Question objects which were previously answered incorrectly
     */
    public List<Question> getIncorrectQuestions() {
        List<Question> incorrectQuestions = new ArrayList<>();
        for (Question question : allQuestions) {
            if (question.getCorrect() == 1) {
                incorrectQuestions.add(question);
            }
        }
        return incorrectQuestions;
    }

    /**
     *
     * @param type defines the type of question to return
     * @return returns a list of Question objects with specified type
     */
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

    /**
     *
     * @param topic defines the topic of the questions to return
     * @return returns a list of Question objects with specified type
     */
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


    /**
     *
     * @param length defines the list and set sizes.
     * @param questionList defines the list of Question objects to randomise order.
     * @return returns a list of distinct question ids with random order.
     */
    public static List<Integer> randomIndexList(int length, List<Question> questionList){
        // length refers to Quiz length
        // List to get index of random integers in the set
        List<Integer> randomIndexList = new ArrayList<>();
        // Set to store distinct random integers
        Set<Integer> randomIndexesSet = new HashSet<>();

        List<Integer> ranQuestionsIdList = new ArrayList<>();

        // Adds distinct random integers to randomIndex list and set given length.
        // Limit determines how many random numbers to generate
        while (randomIndexesSet.size() < length ) {
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

    /**
     *
     * @param questions defines list of Question objects to be used in the randomIndexList()
     * @return returns array of integers made of distinct and random
     * question ids
     */
    public static int[] questionsToIdArray(List<Question> questions) {
        int length = questions.size();
        List<Integer> randomIndexList = randomIndexList(length, questions);
        return randomIndexList.stream().mapToInt(i -> i).toArray();
    }

    // Returns an array of questionsId taking in account if there are enough questions
    // by type or topic
    private int[] queryLength(int length, Session session, Query<Question> query) {
        List<Question> generatedQuestionsList;
        int[] generatedQuestionsIdArray;
        if(query.list().size() >= length){
            generatedQuestionsList = query.setMaxResults(length).stream().toList();
        } else {
            System.out.println("There are not enough questions by the parameters selected.\nQuiz length set to: "
            + query.list().size());
            generatedQuestionsList = query.stream().toList();
        }

        session.getTransaction().commit();
        session.close();

        generatedQuestionsIdArray = questionsToIdArray(generatedQuestionsList);

        return generatedQuestionsIdArray;
    }


    // Returns an array of questions id given topic and whether to use incorrect questions
    // Takes in account whether there are enough questions of the same topic
    // If there are not enough questions, return how many questions there are
    public int[] randomQuestionsId(String topic, int correct, int length){
        int[] generatedQuestionsIdArray;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        // Only gets incorrect or correct questions if they are required
        Query<Question> query;
        if(correct == 1 | correct == 2) {
             query = session.createQuery("from Question where " +
                    "topic= :topic and correct= :correct");
            query.setParameter("correct", correct);
        } else {
            query = session.createQuery("from Question where topic= :topic");
        }
        query.setParameter("topic", topic);


        generatedQuestionsIdArray = queryLength(length, session, query);
        return generatedQuestionsIdArray;
    }


    // Returns an array of questions id given type and whether to use incorrect questions
    // Takes in account whether there are enough questions of the same topic
    // If there are not enough questions, return how many questions there are
    // This can be handled in QuizIO
    public int[] randomQuestionsId(int type, int correct, int length){
        int[] generatedQuestionsIdArray;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        Query<Question> query;
        if(correct == 1 | correct == 2) {
            query = session.createQuery("from Question where type = :type " +
                    "and correct = :correct ");
            query.setParameter("correct", correct);
        } else {
            query = session.createQuery("from Question where type = :type");
        }
        query.setParameter("type", type);


        // Sets the size of the query to match the length of the quiz if
        // there are more questions queried than required
        generatedQuestionsIdArray = queryLength(length, session, query);
        return generatedQuestionsIdArray;
    }

    // Returns array of random questions ids if incorrect questions are specified
    public int[] randomQuestionsIdIncorrect(){
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
    public int[] randomQuestionsId(String topic, int type, int correct, int length){
        int[] generatedQuestionsIdArr;

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        Query<Question> query;
        if(correct == 1 | correct ==2) {
            query = session.createQuery("from Question where type= :type " +
                    "and topic= :topic and correct= :correct");
            query.setParameter("correct", correct);
        } else {
            query = session.createQuery("from Question where type= :type and topic= :topic");
        }
        query.setParameter("type", type);
        query.setParameter("topic", topic);

        generatedQuestionsIdArr = queryLength(length, session, query);
        return generatedQuestionsIdArr;
    }
}
