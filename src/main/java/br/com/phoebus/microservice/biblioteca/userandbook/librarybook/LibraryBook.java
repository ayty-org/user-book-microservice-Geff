package br.com.phoebus.microservice.biblioteca.userandbook.librarybook;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "library_book")
@Builder(builderClassName = "Builder")
public class LibraryBook implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "book_id")
    private Long id;

    private boolean borrowed;

    private String title;

    private String resume;

    private String isbn;

    private String author;

    private int year;

    public static LibraryBook to(LibraryBookDTO libraryBookDTO) {
        return LibraryBook.builder()
                .id(libraryBookDTO.getId())
                .borrowed(libraryBookDTO.isBorrewed())
                .title(libraryBookDTO.getTitle())
                .resume(libraryBookDTO.getResume())
                .isbn(libraryBookDTO.getIsbn())
                .author(libraryBookDTO.getAuthor())
                .year(libraryBookDTO.getYear())
                .build();
    }

    public static List<LibraryBook> to(List<LibraryBookDTO> bookDTOList) {
        List<LibraryBook> libraryBookList = new ArrayList<>();
        for (LibraryBookDTO libraryBookDTO : bookDTOList) {
            libraryBookList.add(LibraryBook.to(libraryBookDTO));
        }
        return libraryBookList;
    }

    public static Page<LibraryBook> to(Page<LibraryBookDTO> pages) {
        return pages.map(LibraryBook::to);
    }

}
