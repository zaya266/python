package eis;

// Data Layer
class Doctor {
    String id;
    String name;
    String specialization;

    Doctor(String id, String name, String specialization) {
        this.id = id;
        this.name = name;
        this.specialization = specialization;
    }

    @Override
    public String toString() {
        return "Dr. " + name + " – " + specialization;
    }
}
