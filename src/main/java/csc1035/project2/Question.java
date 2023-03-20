package csc1035.project2;

import org.hibernate.Session;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Questions")
public class Question {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id; // the primary key

    @Column(name = "question")
    private String question; // the question

    @Column(name = "topic")
    private String topic; // question topic e.g. programming, architecture, etc

    @Column(name = "type")
    private int type; // 1 = mcq, 2 = saq

    @Column(name = "answer")
    private String answer; // the right answer

    // other options for mcqs
    @Column(name = "other1")
    private String other1; // null if saq

    @Column(name = "other2")
    private String other2; // null if saq

    @Column(name = "other3")
    private String other3; // null if saq

    @Column(name = "correct")
    private int correct; // did the user get this question right the last time they attempted it
    // 1 if wrong last time, 2 if correct last time, 0 if never attempted

    public String getTopic(){
        return this.topic;
    }

    public int getType(){
        return this.type;
    }

    public int getCorrect(){return this.correct;}

    public String getQuestion(){return this.question;}

    public int getId(){return this.id;}

    public String getAnswer() {
        return answer;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setCorrect(int val) {
        this.correct = val;
    }

    // constructor for saq
    public Question(String question, String topic, int type, String answer) {
        this.question = question;
        this.topic = topic;
        this.type = type;
        this.answer = answer;
        this.other1 = null;
        this.other2 = null;
        this.other3 = null;
        this.correct = 0;
    }

    // constructor for mcq
    public Question(String question, String topic, int type, String answer, ArrayList<String> otherAnswers) {
        this.question = question;
        this.topic = topic;
        this.type = type;
        this.answer = answer;
        this.other1 = otherAnswers.get(0);
        this.other2 = otherAnswers.get(1);
        this.other3 = otherAnswers.get(2);
        this.correct = 0;
    }

    public Question() {

    }

    @Override
    public String toString() {
        if (type == 1) {
            return "Question: " + question +
                    "\nTopic: " + topic +
                    "\nType: Multiple Choice Question" +
                    "\nAnswer: " + answer +
                    "\nIncorrect Answer 1: " + other1 +
                    "\nIncorrect Answer 2: " + other2 +
                    "\nIncorrect Answer 3: " + other3;
        } else {
            return "Question: " + question +
                    "\nTopic: " + topic +
                    "\nType: Short Answer Question" +
                    "\nAnswer: " + answer;
        }
    }

    public void save() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(this);
        session.getTransaction().commit();
    }

    public void update() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.saveOrUpdate(this);
        session.getTransaction().commit();
    }

    public List<String> getAnswers() {
        return List.of(new String[]{answer, other1, other2, other3});
    }
}
