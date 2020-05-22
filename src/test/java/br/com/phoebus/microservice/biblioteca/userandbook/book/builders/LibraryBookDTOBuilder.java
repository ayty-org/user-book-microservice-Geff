package br.com.phoebus.microservice.biblioteca.userandbook.book.builders;

import br.com.phoebus.microservice.biblioteca.userandbook.librarybook.LibraryBookDTO;

public class LibraryBookDTOBuilder {

    public static LibraryBookDTO.Builder createLibraryBookDTO() {
        return LibraryBookDTO.builder()
                .id(1L)
                .author("Geff author")
                .borrowed(false)
                .isbn("ISBN")
                .resume("Resume")
                .specificIDLoan(null)
                .title("title")
                .year(2020);
    }
}
