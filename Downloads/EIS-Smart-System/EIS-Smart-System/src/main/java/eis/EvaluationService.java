package eis;

import java.util.ArrayList;
import java.util.List;

// Service Layer
class EvaluationService implements Evaluatable {
    List<Evaluation> evaluations = new ArrayList<>();

    @Override
    public void submitEvaluation(Student student, CourseInfo course, int rating, String comment) {
        evaluations.add(new Evaluation(student, course, rating, comment));
        Log.println(student.name + " submitted evaluation for " + course.title);
    }

    @Override
    public void showCourseFeedback(CourseInfo course) {
        Log.println("\nFeedback for: " + course.title);
        double sum = 0;
        int count = 0;
        for (Evaluation e : evaluations) {
            if (e.course.equals(course)) {
                Log.println("- " + e.student.name + ": " + e.rating + "★ — " + e.comment);
                sum += e.rating;
                count++;
            }
        }
        if (count > 0) {
            Log.println("Average Rating: " + String.format("%.2f", sum / count));
        } else {
            Log.println("No evaluations yet.");
        }
    }
}
