package eis;

// Data Layer
class Student {
    String id;
    String name;
    String department;

    Student(String id, String name) {
        this(id, name, "");
    }

    Student(String id, String name, String department) {
        this.id = id;
        this.name = name;
        this.department = department;
    }
}
