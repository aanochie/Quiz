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

    @Column(name = "title")
    private String title;

    @Column(name = "length")
    private int length;
    // Number of questions
    // For number of questions in quiz return int

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
    public Quiz(int[] generatedQuestionId, String title) {
        Field[] fields = this.getClass().getDeclaredFields();
        // j is initialized as 2 so id and score are not counted in the loop
        // To check this println(fields[0]+\n fields[1]) should show Quiz.id and Quiz.score
        for(int i=0, j=3; i < generatedQuestionId.length; i++, j++){
            try{
                fields[j].setAccessible(true);
                fields[j].set(this, generatedQuestionId[i]);
            }catch(IllegalAccessException e){
                System.out.println("Illegal Access Exception");
            }
        }
        this.score = 0;
        this.title = title;
        this.length = generatedQuestionId.length;
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
