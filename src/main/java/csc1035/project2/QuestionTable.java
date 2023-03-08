package csc1035.project2;

import javax.persistence.*;

@Entity
@Table(name = "Questions")
public class QuestionTable {
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
    private int correct = 0; // did the user get this question right the last time they attempted it
    // 1 if wrong last time, 2 if correct last time, 0 if never attempted

    // constructor for saq
    public QuestionTable(String question, String topic, int type, String answer) {
        this.question = question;
        this.topic = topic;
        this.type = type;
        this.answer = answer;
        this.other1 = null;
        this.other2 = null;
        this.other3 = null;
    }

    // constructor for mcq
    public QuestionTable(String question, String topic, int type, String answer, String other1, String other2, String other3) {
        this.question = question;
        this.topic = topic;
        this.type = type;
        this.answer = answer;
        this.other1 = other1;
        this.other2 = other2;
        this.other3 = other3;
    }

    public QuestionTable() {

    }


    @Override
    public String toString() {
        return "QuestionTable{" +
                "id=" + id +
                ", question='" + question + '\'' +
                ", topic='" + topic + '\'' +
                ", type=" + type +
                ", answer='" + answer + '\'' +
                ", other1='" + other1 + '\'' +
                ", other2='" + other2 + '\'' +
                ", other3='" + other3 + '\'' +
                '}';
    }
}
