import java.util.*;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.io.IOException;

public class Co5 {

    static class Student implements Comparable<Student> {
        String name;
        int score;

        Student(String name, int score) {
            this.name = name;
            this.score = score;
        }

        public int compareTo(Student other) {
            return name.compareToIgnoreCase(other.name);
        }

        public String toString() {
            return name + " | Score: " + score;
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);

        String[] questions = {
            "Java is an object-oriented language",
            "Java supports multiple inheritance through classes",
            "String is immutable in Java"
        };

        String[] answers = {"true", "false", "true"};

        System.out.print("Enter student name: ");
        String name = sc.nextLine().trim();

        System.out.println("Enter answers separated by commas (true/false):");
        String input = sc.nextLine();

        // split() and trim()
        String[] studentAnswers = input.split(",", -1);

        int score = 0;
        StringBuilder report = new StringBuilder();

        report.append("Student: ").append(name).append("\n");
        report.append("QUIZ REPORT\n");

        for (int i = 0; i < questions.length; i++) {
            String answer = "";

            if (i < studentAnswers.length) {
                answer = studentAnswers[i].trim();
            }

            boolean correct =
                answer.equalsIgnoreCase(answers[i]);

            if (correct) {
                score++;
            }

            report.append("Q").append(i + 1).append(": ")
                  .append(correct ? "Correct" : "Incorrect")
                  .append("\n");
        }

        report.append("Final Score: ")
              .append(score).append("/")
              .append(questions.length).append("\n");

        // String.join()
        String joinedAnswers = String.join(", ", studentAnswers);
        report.append("Answers: ").append(joinedAnswers).append("\n");

        // Arrays.sort() and Arrays.binarySearch()
        Student[] students = {
            new Student(name, score)
        };

        Arrays.sort(students);

        Student key = new Student(name, 0);
        int index = Arrays.binarySearch(students, key);

        if (index >= 0) {
            report.append("Search Result: ")
                  .append(students[index]).append("\n");
        }

        // Save the report using UTF-8
        Path path = Paths.get("quiz_report.txt");

        Files.writeString(
            path,
            report.toString(),
            StandardCharsets.UTF_8
        );

        System.out.println("\n" + report);
        System.out.println("Report saved to quiz_report.txt");

        sc.close();
    }
}