package br.com.phoebus.microservice.biblioteca.userandbook.book;

import br.com.phoebus.microservice.biblioteca.userandbook.librarybook.LibraryBook;
import br.com.phoebus.microservice.biblioteca.userandbook.librarybook.LibraryBookDTO;
import br.com.phoebus.microservice.biblioteca.userandbook.librarybook.LibraryBookRepository;
import br.com.phoebus.microservice.biblioteca.userandbook.librarybook.service.SaveLibraryBookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static br.com.phoebus.microservice.biblioteca.userandbook.book.builders.LibraryBookDTOBuilder.createLibraryBookDTO;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.verify;

@Tag("Service")
@ExtendWith(MockitoExtension.class)
@DisplayName("Valida a funcionalidade do serviço em salvar um livro")
public class SaveLibraryBookServiceTest {

    @Mock
    private LibraryBookRepository repository;

    private SaveLibraryBookServiceImpl saveLibraryBookService;

    @BeforeEach
    void setUp() {
        this.saveLibraryBookService = new SaveLibraryBookServiceImpl(repository);
    }

    @Test
    @DisplayName("Salva um livro")
    void shouldSaveBook() {
        LibraryBookDTO libraryBookDTO = createLibraryBookDTO().id(null).build();

        saveLibraryBookService.saveLibraryBook(libraryBookDTO);

        ArgumentCaptor<LibraryBook> captorBook = ArgumentCaptor.forClass(LibraryBook.class);
        verify(repository).save(captorBook.capture());

        LibraryBook result = captorBook.getValue();

        assertAll("Book",
                () -> assertThat(result.getSpecificIDLoan(), is(libraryBookDTO.getSpecificIDLoan())),
                () -> assertThat(result.getYear(), is(libraryBookDTO.getYear())),
                () -> assertThat(result.isBorrowed(), is(libraryBookDTO.isBorrowed())),
                () -> assertThat(result.getTitle(), is(libraryBookDTO.getTitle())),
                () -> assertThat(result.getResume(), is(libraryBookDTO.getResume())),
                () -> assertThat(result.getAuthor(), is(libraryBookDTO.getAuthor())),
                () -> assertThat(result.getIsbn(), is(libraryBookDTO.getIsbn()))
        );

    }
}
