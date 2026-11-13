package eis;

// Service Layer
interface Recordable {
    void recordAttendance(Student student, String date, boolean present);
}
