package csc1035.project2;
import java.util.ArrayList;
import java.util.List;

public class Listing {
    private List<ArrayList> questions; // 创建一个List对象用于存储Question对象

    public QuestionManager() {
        questions = new ArrayList<>(); // 初始化List对象
    }

    public void addQuestion(Question question) {
        questions.add(question); // 将Question对象添加到List对象中
    }

    public List<Question> getAllQuestions() {
        return questions; // 返回存储所有Question对象的List对象
    }

    public List<Question> getQuestionsByType(QuestionType type) {
        List<Question> questionsByType = new ArrayList<>(); // 创建一个新的List对象用于存储指定类型的Question对象

        for (Question question : questions) {
            if (question.getType() == type) { // 如果Question对象的类型与指定类型相同
                questionsByType.add(question); // 将Question对象添加到新的List对象中
            }
        }

        return questionsByType; // 返回存储指定类型的Question对象的List对象
    }

    public List<Question> getQuestionsByTopic(String topic) {
        List<Question> questionsByTopic = new ArrayList<>(); // 创建一个新的List对象用于存储指定主题的Question对象

        for (Question question : questions) {
            if (question.getTopic().equals(topic)) { // 如果Question对象的主题与指定主题相同
                questionsByTopic.add(question); // 将Question对象添加到新的List对象中
            }
        }

        return questionsByTopic; // 返回存储指定主题的Question对象的List对象
    }
}
