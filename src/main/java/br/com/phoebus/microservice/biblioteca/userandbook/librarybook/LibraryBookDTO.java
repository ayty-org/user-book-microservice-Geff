package br.com.phoebus.microservice.biblioteca.userandbook.librarybook;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(builderClassName = "Builder")
public class LibraryBookDTO {

    private Long id;

    @NotEmpty(message = "Title may not be empty")
    private String title;

    @NotEmpty(message = "Resume may not be empty")
    @Size(max = 500)
    private String resume;

    @NotEmpty(message = "ISBN may not be empty")
    private String isbn;

    @NotEmpty(message = "Author may not be empty")
    private String author;

    @Min(1300)
    private int year;

    public static LibraryBookDTO from(LibraryBook libraryBook) {
        return LibraryBookDTO.builder()
                .id(libraryBook.getId())
                .title(libraryBook.getTitle())
                .resume(libraryBook.getResume())
                .isbn(libraryBook.getIsbn())
                .author(libraryBook.getAuthor())
                .year(libraryBook.getYear())
                .build();
    }

    public static List<LibraryBookDTO> from(List<LibraryBook> bookList) {
        List<LibraryBookDTO> libraryBookDTOList = new ArrayList<>();
        for (LibraryBook libraryBook : bookList) {
            libraryBookDTOList.add(LibraryBookDTO.from(libraryBook));
        }
        return libraryBookDTOList;
    }

    public static Page<LibraryBookDTO> from(Page<LibraryBook> pages) {
        return pages.map(LibraryBookDTO::from);
    }
}
