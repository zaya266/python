package eis;

// Data Layer
class Book {
    String id;
    String title;
    String author;
    boolean available = true;

    Book(String id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }

    @Override
    public String toString() {
        return title + " — " + author + (available ? "" : "  (зээлсэн)");
    }
}
