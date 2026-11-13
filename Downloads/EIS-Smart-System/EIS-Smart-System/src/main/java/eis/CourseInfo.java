package eis;

// Data Layer
class CourseInfo {
    String id;
    String title;
    String instructor;

    CourseInfo(String id, String title, String instructor) {
        this.id = id;
        this.title = title;
        this.instructor = instructor;
    }

    @Override
    public String toString() {
        return title + " (" + instructor + ")";
    }
}
