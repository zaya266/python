package eis;

import java.util.ArrayList;
import java.util.List;

// Service Layer
class DormitoryService implements Assignable {
    List<DormRecord> records = new ArrayList<>();

    @Override
    public void assignRoom(Student student, Room room, String dateIn) {
        if (!room.occupied) {
            room.occupied = true;
            records.add(new DormRecord(student, room, dateIn, null));
            Log.println(student.name + " assigned to Room " + room.roomNumber);
        } else {
            Log.println("Room " + room.roomNumber + " is already occupied.");
        }
    }

    @Override
    public void releaseRoom(Student student, Room room, String dateOut) {
        for (DormRecord r : records) {
            if (r.student.equals(student) && r.room.equals(room) && r.dateOut == null) {
                room.occupied = false;
                r.dateOut = dateOut;
                Log.println(student.name + " checked out from Room " + room.roomNumber);
                return;
            }
        }
        Log.println("No active record: " + student.name + " is not living in Room " + room.roomNumber);
    }

    void showRoomStatus() {
        Log.println("\nDormitory Status:");
        if (records.isEmpty()) {
            Log.println("No records yet.");
        }
        for (DormRecord r : records) {
            Log.println("- " + r.student.name + " → Room " + r.room.roomNumber
                    + " (" + (r.dateOut == null ? "Active" : "Checked out") + ")");
        }
    }
}
