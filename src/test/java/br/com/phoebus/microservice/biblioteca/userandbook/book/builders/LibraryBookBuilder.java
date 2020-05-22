package br.com.phoebus.microservice.biblioteca.userandbook.book.builders;

import br.com.phoebus.microservice.biblioteca.userandbook.librarybook.LibraryBook;

public class LibraryBookBuilder {

    public static LibraryBook.Builder createLibraryBook() {
        return LibraryBook.builder()
                .id(1L)
                .author("Author Geff")
                .borrowed(false)
                .isbn("ISBN")
                .resume("Resume")
                .title("Title")
                .specificIDLoan(null)
                .year(1997);
    }
}
