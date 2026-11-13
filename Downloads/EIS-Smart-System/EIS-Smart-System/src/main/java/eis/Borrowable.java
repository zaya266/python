package eis;

// Service Layer
interface Borrowable {
    void borrowBook(Member member, Book book);

    void returnBook(Member member, Book book);
}
