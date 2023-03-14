package csc1035.project2;
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
}
