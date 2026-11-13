package eis;

// Service Layer
interface Assignable {
    void assignRoom(Student student, Room room, String dateIn);

    void releaseRoom(Student student, Room room, String dateOut);
}
