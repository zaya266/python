package eis;

// Service Layer
interface Evaluatable {
    void submitEvaluation(Student student, CourseInfo course, int rating, String comment);

    void showCourseFeedback(CourseInfo course);
}
