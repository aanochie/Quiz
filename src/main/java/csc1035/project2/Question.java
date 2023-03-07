package csc1035.project2;

import javax.persistence.*;

@Entity
@Table(name = "Questions")
public class Question {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "question")
    private String question; // the question

    @Column(name = "topic")
    private String topic; // question topic e.g. programming, architecture, etc

    @Column(name = "type")
    private int type; // 1 = mcq, 2 = saq
}
