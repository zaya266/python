package eis;

// Data Layer
class Attendance {
    Student student;
    String courseId;
    String date;
    boolean present;

    Attendance(Student student, String courseId, String date, boolean present) {
        this.student = student;
        this.courseId = courseId;
        this.date = date;
        this.present = present;
    }
}
