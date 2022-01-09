import model.Book;
import model.LibraryRoom;
import model.Place;

import java.io.IOException;
import java.time.LocalDate;

import static model.Book.*;

public class Main {

    public static void main(String[] args) throws IOException {

        //book with mandatory attributes
        Book book1 = new Book(1L, "Harry Potter", 240, "fantasy", new Place(LibraryRoom.BEDROOM,"bookCase 1", 2));

        //book with optional attributes
        Book book2 = new Book(2L, "Lord of the Rings",400, "english", "fantasy", LocalDate.now(), new Place(LibraryRoom.LIVINGROOM,"bookCase 2", 3));

        //book with mandatory attributes and date read
        Book book3 = new Book(3L, "Jane Eyre", 300, "romance", new Place(LibraryRoom.STUDY,"bookCase 3", 1));

        //prints extent - all books
        System.out.println(getExtent());

        System.out.println();
        System.out.println("title:" + book1.getTitle()); //simple attribute
        System.out.println("language: " + book2.getLanguage()); //optional attribute
        book2.addGenre("action");
        System.out.println("genres: " + book2.getGenre());//multivalued attributes
        System.out.println("place of book: " + book3.getPlaceOfBook());//complex attribute
        System.out.println("owner of the library is: " + Book.getBelongsTo());//class attribute
        System.out.println("is book2 read? " + book2.isRead() + ". And book 1? " + book1.isRead()); //derived attribute
        System.out.println("\n all fantasy books: " + showByGenre("fantasy")); //class method - filters by genre

        //overloaded methods
        System.out.println(book2.timeToRead());
        System.out.println(book2.timeToRead(500));

    }
}


