package eis;

// Service Layer
class OnlineCourse extends Course {
    String meetingLink;

    OnlineCourse(String courseId, String courseName, String meetingLink) {
        super(courseId, courseName);
        this.meetingLink = meetingLink;
    }

    @Override
    public void recordAttendance(Student student, String date, boolean present) {
        Log.println("Online attendance via link: " + meetingLink);
        super.recordAttendance(student, date, present);
        Log.println(student.name + " → " + (present ? "Present" : "Absent") + " recorded.");
    }
}
