package br.com.phoebus.microservice.biblioteca.userandbook.book;

import br.com.phoebus.microservice.biblioteca.userandbook.exceptions.LibraryBookNotFoundException;
import br.com.phoebus.microservice.biblioteca.userandbook.librarybook.LibraryBook;
import br.com.phoebus.microservice.biblioteca.userandbook.librarybook.LibraryBookDTO;
import br.com.phoebus.microservice.biblioteca.userandbook.librarybook.LibraryBookRepository;
import br.com.phoebus.microservice.biblioteca.userandbook.librarybook.service.GetLibraryBookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static br.com.phoebus.microservice.biblioteca.userandbook.book.builders.LibraryBookBuilder.createLibraryBook;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Tag("Service")
@ExtendWith(MockitoExtension.class)
@DisplayName("Valida a funcionalidade do serviço em buscar um livro")
public class GetLibraryBookServiceTest {

    @Mock
    private LibraryBookRepository repository;

    private GetLibraryBookServiceImpl getLibraryBookService;

    private final Long ID_GET = 1L;

    @BeforeEach
    void setUp() {
        this.getLibraryBookService = new GetLibraryBookServiceImpl(repository);
    }

    @Test
    @DisplayName("Deve retornar um livro")
    void shouldGetBook() {

        Optional<LibraryBook> libraryBook = Optional.of(createLibraryBook().build());
        when(repository.findById(anyLong())).thenReturn(libraryBook);

        LibraryBookDTO result = getLibraryBookService.getLibraryBookForID(ID_GET);

        assertAll("Book",
                () -> assertThat(result.getId(), is(libraryBook.get().getId())),
                () -> assertThat(result.getYear(), is(libraryBook.get().getYear())),
                () -> assertThat(result.getResume(), is(libraryBook.get().getResume())),
                () -> assertThat(result.getTitle(), is(libraryBook.get().getTitle())),
                () -> assertThat(result.getIsbn(), is(libraryBook.get().getIsbn())),
                () -> assertThat(result.getAuthor(), is(libraryBook.get().getAuthor())),
                () -> assertThat(result.getSpecificIDLoan(), is(libraryBook.get().getSpecificIDLoan())),
                () -> assertThat(result.isBorrowed(), is(libraryBook.get().isBorrowed()))
        );
        verify(repository, times(1)).findById(ID_GET);
    }

    @Test
    @DisplayName("Deve lançar uma exceção")
    void shouldLibraryBookNotFoundException() {

        when(repository.findById(anyLong())).thenThrow(new LibraryBookNotFoundException());

        assertThrows(LibraryBookNotFoundException.class, () -> getLibraryBookService.getLibraryBookForID(ID_GET));

        verify(repository, times(1)).findById(ID_GET);
    }
}
