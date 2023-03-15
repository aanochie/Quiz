package csc1035.project2;
import org.hibernate.Session;
import javax.persistence.*;
import java.lang.reflect.Field;
import java.util.List;

/**
 * This class represents a table called "Quiz", which holds space for a unique quiz id, each question id and the most recent score achieved.
 * @author nasimhannan
 */
@Entity
@Table(name = "Quizzes")
public class Quiz {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id; // primary key

    @Column(name = "score")
    private int score; // Users previous score of set quiz

    @Column(name = "qid_1")
    private int qid_1; // question id 1 

    @Column(name = "qid_2")
    private int qid_2; // question id 2 

    @Column(name = "qid_3")
    private int qid_3; // question id 3 

    @Column(name = "qid_4")
    private int qid_4; // question id 4 

    @Column(name = "qid_5")
    private int qid_5; // question id 5 

    @Column(name = "qid_6")
    private int qid_6; // question id 6 - "-1" if 5 question quiz

    @Column(name = "qid_7")
    private int qid_7; // question id 7 - "-1" if 5 question quiz

    @Column(name = "qid_8")
    private int qid_8; // question id 8 - "-1" if 5 question quiz

    @Column(name = "qid_9")
    private int qid_9; // question id 9 - "-1" if 5 question quiz

    @Column(name = "qid_10")
    private int qid_10; // question id 10 - "-1" if 5 question quiz

    @Column(name = "qid_11")
    private int qid_11; // question id 11 - "-1" if 5/10 question quiz

    @Column(name = "qid_12")
    private int qid_12; // question id 12 - "-1" if 5/10 question quiz

    @Column(name = "qid_13")
    private int qid_13; // question id 13 - "-1" if 5/10 question quiz

    @Column(name = "qid_14")
    private int qid_14; // question id 14 - "-1" if 5/10 question quiz

    @Column(name = "qid_15")
    private int qid_15; // question id 15 - "-1" if 5/10 question quiz

    @Column(name = "qid_16")
    private int qid_16; // question id 16 - "-1" if 5/10/15 question quiz

    @Column(name = "qid_17")
    private int qid_17; // question id 17 - "-1" if 5/10/15 question quiz

    @Column(name = "qid_18")
    private int qid_18; // question id 18 - "-1" if 5/10/15 question quiz

    @Column(name = "qid_19")
    private int qid_19; // question id 19 - "-1" if 5/10/15 question quiz

    @Column(name = "qid_20")
    private int qid_20; // question id 20 - "-1" if 5/10/15 question quiz


    /**
     * This constructor makes up for a 5-question quiz and assigns each question to its specific id
     * @param generatedQuestionId
     */
    // 5 Question constructor
    // Quiz constructor to set generated questions
    // set this.qid_n = qid_n
    // Where qid_n comes from a list of generated questions id
    public Quiz(List<Integer> generatedQuestionId) {
        Field[] fields = this.getClass().getDeclaredFields();
        // j is initialized as 2 so id and score are not counted in the loop
        // To check this println(fields[0]+\n fields[1]) should show Quiz.id and Quiz.score
        for(int i=0, j=2; i < generatedQuestionId.size() && j < generatedQuestionId.size(); i++, j++){
            try{
                fields[j].setAccessible(true);
                fields[j].set(this, generatedQuestionId.get(i));
                if(j-2>i){
                    fields[j].set(this, null);
                }
            }catch(IllegalAccessException e){
                System.out.println("Illegal Access Exception");
            }

        }
        this.score = 0;
    }

    /**
     * This constructor makes up for a 10-question quiz and assigns each question to its specific id
     * @param qid_1
     * @param qid_2
     * @param qid_3
     * @param qid_4
     * @param qid_5
     * @param qid_6
     * @param qid_7
     * @param qid_8
     * @param qid_9
     * @param qid_10
     */
    // 10 Question constructor
    public Quiz(int qid_1, int qid_2, int qid_3, int qid_4, int qid_5, int qid_6, int qid_7, int qid_8, int qid_9, int qid_10) {
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
        this.score = 0;
    }

    /**
     * This constructor makes up for a 15-question quiz and assigns each question to its specific id
     * @param qid_1
     * @param qid_2
     * @param qid_3
     * @param qid_4
     * @param qid_5
     * @param qid_6
     * @param qid_7
     * @param qid_8
     * @param qid_9
     * @param qid_10
     * @param qid_11
     * @param qid_12
     * @param qid_13
     * @param qid_14
     * @param qid_15
     */
    // 15 Question constructor
    public Quiz(int qid_1, int qid_2, int qid_3, int qid_4, int qid_5, int qid_6, int qid_7, int qid_8, int qid_9, int qid_10, int qid_11, int qid_12, int qid_13, int qid_14, int qid_15) {
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
        this.score = 0;
    }

    /**
     * This constructor makes up for a 20-question quiz and assigns each question to its specific id
     * @param qid_1
     * @param qid_2
     * @param qid_3
     * @param qid_4
     * @param qid_5
     * @param qid_6
     * @param qid_7
     * @param qid_8
     * @param qid_9
     * @param qid_10
     * @param qid_11
     * @param qid_12
     * @param qid_13
     * @param qid_14
     * @param qid_15
     * @param qid_16
     * @param qid_17
     * @param qid_18
     * @param qid_19
     * @param qid_20
     */
    // 20 Question constructor
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
        this.score = 0;
    }

    /**
     * This constructor makes up for an empty quiz entry to prevent errors and clashes
     */
    public Quiz() {

    }

    public static void newRow() {
        Quiz addRow = new Quiz();
        addRow.save();
    }

    public void save() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(this);
        session.getTransaction().commit();
    }

    public void setScore(Integer score){
        this.score =score;
    }

    public int getID(){
        return this.id;
    }

}

