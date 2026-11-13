package eis;

import java.util.ArrayList;
import java.util.List;

// Service Layer
class LibraryService implements Borrowable {
    List<Book> books = new ArrayList<>();

    void addBook(Book book) {
        books.add(book);
    }

    @Override
    public void borrowBook(Member member, Book book) {
        if (book.available) {
            book.available = false;
            Log.println(member.name + " borrowed \"" + book.title + "\"");
        } else {
            Log.println("Sorry, the book \"" + book.title + "\" is not available.");
        }
    }

    @Override
    public void returnBook(Member member, Book book) {
        book.available = true;
        Log.println(member.name + " returned \"" + book.title + "\"");
    }

    void showAvailableBooks() {
        Log.println("\nAvailable Books:");
        for (Book b : books) {
            if (b.available) {
                Log.println("- " + b.title + " by " + b.author);
            }
        }
    }
}
