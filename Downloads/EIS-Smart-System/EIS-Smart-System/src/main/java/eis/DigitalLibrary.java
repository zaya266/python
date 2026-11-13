package eis;

// Service Layer
class DigitalLibrary extends LibraryService {
    String websiteLink;

    DigitalLibrary(String websiteLink) {
        this.websiteLink = websiteLink;
    }

    @Override
    public void borrowBook(Member member, Book book) {
        Log.println("Accessing digital library at: " + websiteLink);
        super.borrowBook(member, book);
    }
}
