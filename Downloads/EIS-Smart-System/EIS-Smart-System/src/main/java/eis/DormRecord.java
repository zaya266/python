package eis;

// Data Layer
class DormRecord {
    Student student;
    Room room;
    String dateIn;
    String dateOut;

    DormRecord(Student student, Room room, String dateIn, String dateOut) {
        this.student = student;
        this.room = room;
        this.dateIn = dateIn;
        this.dateOut = dateOut;
    }
}
