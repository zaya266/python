package eis;

// Data Layer
class Room {
    int roomNumber;
    boolean occupied = false;

    Room(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    @Override
    public String toString() {
        return "Өрөө " + roomNumber + (occupied ? "  – эзэнтэй" : "  – сул");
    }
}
