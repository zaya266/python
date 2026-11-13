package eis;

import java.util.ArrayList;
import java.util.List;

// Service Layer
class Course implements Recordable {
    String courseId;
    String courseName;
    List<Attendance> records = new ArrayList<>();

    Course(String courseId, String courseName) {
        this.courseId = courseId;
        this.courseName = courseName;
    }

    @Override
    public void recordAttendance(Student student, String date, boolean present) {
        records.add(new Attendance(student, courseId, date, present));
    }

    void showRecords() {
        Log.println("\nAttendance records – " + courseName + ":");
        if (records.isEmpty()) {
            Log.println("No records yet.");
        }
        for (Attendance a : records) {
            Log.println(studentInfo(a));
        }
    }

    private String studentInfo(Attendance a) {
        return a.date + " | " + a.student.name + " → " + (a.present ? "Present" : "Absent");
    }
}
