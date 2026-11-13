package eis;

// Data Layer
class Evaluation {
    Student student;
    CourseInfo course;
    int rating; // 1–5
    String comment;

    Evaluation(Student student, CourseInfo course, int rating, String comment) {
        this.student = student;
        this.course = course;
        this.rating = rating;
        this.comment = comment;
    }
}
