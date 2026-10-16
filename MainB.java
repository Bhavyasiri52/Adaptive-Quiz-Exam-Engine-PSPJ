
import java.util.Scanner;

// Interface
interface Exam {
    void startExam();
}

// Parent class
class Question {
    private String question;
    private String[] options;
    private int correctAnswer;
    private int difficulty;

    // Static variable
    static int totalQuestions = 0;

    // Default constructor
    Question() {
        this("No question", new String[]{"A", "B", "C", "D"}, 1, 1);
    }

    // Parameterized constructor
    Question(String question, String[] options,
             int correctAnswer, int difficulty) {
        this.question = question;
        this.options = options;
        this.correctAnswer = correctAnswer;
        this.difficulty = difficulty;
        totalQuestions++;
    }

    // Accessors
    public String getQuestion() {
        return question;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public String[] getOptions() {
        return options;
    }

    public boolean checkAnswer(int answer)
            throws IllegalArgumentException {
        if (answer < 1 || answer > 4) {
            throw new IllegalArgumentException("Invalid option!");
        }
        return answer == correctAnswer;
    }

    // Object representation
    public String toString() {
        return question + " (Difficulty: " + difficulty + ")";
    }
}

// Inheritance
class AdaptiveQuestion extends Question {
    AdaptiveQuestion(String q, String[] options,
                     int answer, int difficulty) {
        super(q, options, answer, difficulty);
    }

    // Method overriding
    @Override
    public String toString() {
        return "Adaptive Question: " + super.toString();
    }
}

// Exam implementation
class QuizEngine implements Exam {
    private Question[] questions;
    private int score = 0;
    private int currentDifficulty = 1;

    static final int PASS_MARK = 2;

    QuizEngine(Question[] questions) {
        this.questions = questions;
    }

    // Static method
    static void showTitle() {
        System.out.println("=== Adaptive Quiz & Exam Engine ===");
    }

    @Override
    public void startExam() {
        Scanner sc = new Scanner(System.in);

        for (Question q : questions) {
            if (q == null) {
                System.out.println("Question is missing!");
                continue;
            }

            // Adapt difficulty
            if (q.getDifficulty() != currentDifficulty) {
                System.out.println(
                    "\nDifficulty changed to " + q.getDifficulty());
            }

            System.out.println("\n" + q);
            String[] options = q.getOptions();

            for (int i = 0; i < options.length; i++) {
                System.out.println((i + 1) + ". " + options[i]);
            }

            try {
                System.out.print("Enter your answer (1-4): ");
                int answer = Integer.parseInt(sc.nextLine());

                if (q.checkAnswer(answer)) {
                    System.out.println("Correct!");
                    score++;
                    currentDifficulty = Math.min(3, q.getDifficulty() + 1);
                } else {
                    System.out.println("Incorrect!");
                    currentDifficulty = Math.max(1, q.getDifficulty() - 1);
                }

            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            } finally {
                System.out.println("Answer processing completed.");
            }
        }

        System.out.println("\n=== Exam Result ===");
        System.out.println("Score: " + score + "/" + questions.length);

        if (score >= PASS_MARK) {
            System.out.println("Result: PASS");
        } else {
            System.out.println("Result: FAIL");
        }

        System.out.println("Total questions created: "
                           + Question.totalQuestions);
    }
}

// Main class
public class MainB {
    public static void main(String[] args) {
        QuizEngine.showTitle();

        Question[] questions = {
            new AdaptiveQuestion(
                "Which keyword creates an object?",
                new String[]{"class", "new", "static", "void"},
                2, 1),

            new AdaptiveQuestion(
                "Which concept hides data?",
                new String[]{"Inheritance", "Polymorphism",
                             "Encapsulation", "Looping"},
                3, 2),

            new AdaptiveQuestion(
                "Which block always executes?",
                new String[]{"try", "catch", "finally", "throw"},
                3, 3)
        };

        Exam exam = new QuizEngine(questions);

        try {
            exam.startExam();
        } catch (Exception e) {
            System.out.println("Exam error: " + e.getMessage());
        }
    }
}
