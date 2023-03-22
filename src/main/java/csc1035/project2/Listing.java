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
        // List to get index of random integers in the set
        List<Integer> randomIndexList = new ArrayList<>();
        // Set to store distinct random integers
        Set<Integer> randomIndexesSet = new HashSet<>();

        List<Integer> ranQuestionsIdList = new ArrayList<>();

        // Adds distinct random integers to randomIndex list and set given length.
        // length determines how many random numbers to generate
        while (randomIndexesSet.size() < length ) {
            // Creates random number from 0 to amount of questions in the provided list of Questions
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
     * @param questions defines list of Question objects to be used in the randomIndexList().
     * @return returns array of primitive type integers made of distinct and random question ids.
     */
    public static int[] questionsToIdArray(List<Question> questions) {
        int length = questions.size();
        List<Integer> randomIndexList = randomIndexList(length, questions);
        return randomIndexList.stream().mapToInt(i -> i).toArray();
    }

    /**
     *
     * @param length defines the quizLength selected by the user to be compared to the queried list length.
     * @param session defines the initiated hibernate Session
     * @param query defines the hibernate Query which length is going to be set depending on the quiz length.
     * @return returns an array of primitive type integers made of distinct and random question ids.
     */
    private int[] queryLength(int length, Session session, Query<Question> query) {
        List<Question> generatedQuestionsList;
        int[] generatedQuestionsIdArray;

        // Ensures if the quiz length selected exceeds the amount of questions available, sets the length of the
        // query to the amount of questions with the specified type and topic available
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


    /**
     *
     * @param topic defines the question topic to be queried.
     * @param correct defines whether the user wants previously answered incorrect questions to be queried.
     * @param length defines the length of the list of questions queried.
     * @return returns an array of primitive type integers made of distinct and random question ids, specified by the
     * topic and whether to return previously answered incorrect questions.
     */
    public int[] randomQuestionsId(String topic, int correct, int length){
        int[] generatedQuestionsIdArray;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        // Ensures that incorrect questions are only queried when required
        // Allows user to query questions even if they have been previously answered
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



    /**
     *
     * @param type defines the question type to be queried.
     * @param correct defines whether the user wants previously answered incorrect questions to be queried.
     * @param length defines the length of the list of questions queried.
     * @return returns an array of primitive type integers made of distinct and random question ids, specified by the type
     * and whether to return previously answered incorrect questions.
     */
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


        // Sets the size of the query to match the length of the quiz if there are more questions queried than required
        // if there are not enough questions queried then it sets the length to how many questions queried there are.
        generatedQuestionsIdArray = queryLength(length, session, query);
        return generatedQuestionsIdArray;
    }


    /**
     *
     * @return returns array of random questions ids where all the questions have been previously answered incorrectly.
     */
    public int[] randomQuestionsIdIncorrect(){
        List<Question> generatedQuestionsList = getIncorrectQuestions();
        return questionsToIdArray(generatedQuestionsList);
    }


    /**
     *
     * @param length defines the length of the array returned
     * @return returns a random array of primitive type integers with specified length, made of question ids
     * from all questions available.
     */
    public int[] randomQuestionsId(int length) {
        // Set to store distinct questions
        List<Integer> ranQuestionsIdList = new ArrayList<>(randomIndexList(length, allQuestions));
        int[] ranQuestionsIdArr;

        // Converts generated list to array
        ranQuestionsIdArr = ranQuestionsIdList.stream().mapToInt(i -> i).toArray();
        return ranQuestionsIdArr;
    }


    /**
     *
     * @param topic defines the question topic to be queried
     * @param type defines the question type to be queried
     * @param correct defines whether user wants previously answered incorrect questions to be queried
     * @param length defines the length of the list of questions queried
     * @return returns an array of primitive type integers made of distinct and random question ids, with specified
     * topic, type and whether the user wants previously answered incorrect questions.
     */
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
