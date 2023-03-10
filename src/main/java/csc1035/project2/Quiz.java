package csc1035.project2;
import org.hibernate.Session;

import javax.persistence.*;

@Entity
@Table(name = "Quizzes")
public class Quiz {
    @Id
    @GeneratedValue
    @Column(name = "Quiz ID")
    private int id; // primary key

    @Column(name = "Previous Score")
    private int score; // Users previous score of set quiz

    @Column(name = "Question 1 ID")
    private int qid_1; // question id 1 

    @Column(name = "Question 2 ID")
    private int qid_2; // question id 2 

    @Column(name = "Question 3 ID")
    private int qid_3; // question id 3 

    @Column(name = "Question 4 ID")
    private int qid_4; // question id 4 

    @Column(name = "Question 5 ID")
    private int qid_5; // question id 5 

    @Column(name = "Question 6 ID")
    private int qid_6; // question id 6 - "-1" if 5 question quiz

    @Column(name = "Question 7 ID")
    private int qid_7; // question id 7 - "-1" if 5 question quiz

    @Column(name = "Question 8 ID")
    private int qid_8; // question id 8 - "-1" if 5 question quiz

    @Column(name = "Question 9 ID")
    private int qid_9; // question id 9 - "-1" if 5 question quiz

    @Column(name = "Question 10 ID")
    private int qid_10; // question id 10 - "-1" if 5 question quiz

    @Column(name = "Question 11 ID")
    private int qid_11; // question id 11 - "-1" if 5/10 question quiz

    @Column(name = "Question 12 ID")
    private int qid_12; // question id 12 - "-1" if 5/10 question quiz

    @Column(name = "Question 13 ID")
    private int qid_13; // question id 13 - "-1" if 5/10 question quiz

    @Column(name = "Question 14 ID")
    private int qid_14; // question id 14 - "-1" if 5/10 question quiz

    @Column(name = "Question 15 ID")
    private int qid_15; // question id 15 - "-1" if 5/10 question quiz

    @Column(name = "Question 16 ID")
    private int qid_16; // question id 16 - "-1" if 5/10/15 question quiz

    @Column(name = "Question 17 ID")
    private int qid_17; // question id 17 - "-1" if 5/10/15 question quiz

    @Column(name = "Question 18 ID")
    private int qid_18; // question id 18 - "-1" if 5/10/15 question quiz

    @Column(name = "Question 19 ID")
    private int qid_19; // question id 19 - "-1" if 5/10/15 question quiz

    @Column(name = "Question 20 ID")
    private int qid_20; // question id 20 - "-1" if 5/10/15 question quiz

    public Quiz(int qid_1, int qid_2, int qid_3, int qid_4, int qid_5, int qid_6, int qid_7, int qid_8, int qid_9, int qid_10, int qid_11, int qid_12, int qid_13, int qid_14, int qid_15, int qid_16, int qid_17, int qid_18, int qid_19, int qid_20) {
        this.qid_1 = qid_1;
        this.qid_2 = qid_2;
        this.qid_3 = qid_3;
        this.qid_4 = qid_4;
        this.qid_5 = qid_5;
        this.qid_6 = qid_6;
        this.qid_7 = qid_7;
        this.qid_8 = qid_8;
        this.qid_9 = qid_9;
        this.qid_10 = qid_10;
        this.qid_11 = qid_11;
        this.qid_12 = qid_12;
        this.qid_13 = qid_13;
        this.qid_14 = qid_14;
        this.qid_15 = qid_15;
        this.qid_16 = qid_16;
        this.qid_17 = qid_17;
        this.qid_18 = qid_18;
        this.qid_19 = qid_19;
        this.qid_20 = qid_20;
    }

    public Quiz() {

    }

    public static void main(String[] args) {
        Quiz first_quiz = new Quiz(1, 2, 3, 4, 5,6,7,8,9,10,11,12,13,14,15,16, 17, 18,19,20);
        first_quiz.save();
    }

    public void save() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(this);
        session.getTransaction().commit();
    }
}

