package eis;

// Service Layer
class SmartDormitoryService extends DormitoryService {
    String systemLink;

    SmartDormitoryService(String systemLink) {
        this.systemLink = systemLink;
    }

    @Override
    public void assignRoom(Student student, Room room, String dateIn) {
        Log.println("Registering via Smart Dormitory System: " + systemLink);
        super.assignRoom(student, room, dateIn);
    }
}
