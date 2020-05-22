package br.com.phoebus.microservice.biblioteca.userandbook.book;

import br.com.phoebus.microservice.biblioteca.userandbook.exceptions.LibraryBookNotFoundException;
import br.com.phoebus.microservice.biblioteca.userandbook.librarybook.LibraryBook;
import br.com.phoebus.microservice.biblioteca.userandbook.librarybook.LibraryBookDTO;
import br.com.phoebus.microservice.biblioteca.userandbook.librarybook.LibraryBookRepository;
import br.com.phoebus.microservice.biblioteca.userandbook.librarybook.service.EditLibraryBookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static br.com.phoebus.microservice.biblioteca.userandbook.book.builders.LibraryBookBuilder.createLibraryBook;
import static br.com.phoebus.microservice.biblioteca.userandbook.book.builders.LibraryBookDTOBuilder.createLibraryBookDTO;
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
@DisplayName("Valida a funcionalidade do serviço responsavel por editar um livro")
public class EditLibraryBookServiceTest {

    @Mock
    private LibraryBookRepository repository;

    private EditLibraryBookServiceImpl editLibraryBookService;

    private final Long ID_EDIT = 1L;

    @BeforeEach
    void setUp() {
        this.editLibraryBookService = new EditLibraryBookServiceImpl(repository);
    }

    @Test
    @DisplayName("Deve editar um book")
    void shouldEditBook() {

        LibraryBookDTO libraryBookDTO = createLibraryBookDTO()
                .id(ID_EDIT)
                .year(1995)
                .resume("resume edit")
                .isbn("ISBN edit")
                .title("tittle edit")
                .author("Gerson").build();
        Optional<LibraryBook> libraryBook = Optional.of(createLibraryBook().id(ID_EDIT).build());

        when(repository.findById(anyLong())).thenReturn(libraryBook);

        editLibraryBookService.editLibraryBook(ID_EDIT, libraryBookDTO);

        ArgumentCaptor<LibraryBook> captorBook = ArgumentCaptor.forClass(LibraryBook.class);
        verify(repository, times(1)).findById(ID_EDIT);
        verify(repository, times(1)).save(captorBook.capture());

        LibraryBook result = captorBook.getValue();

        assertAll("Book",
                () -> assertThat(result.getId(), is(ID_EDIT)),
                () -> assertThat(result.isBorrowed(), is(libraryBookDTO.isBorrowed())),
                () -> assertThat(result.getAuthor(), is(libraryBookDTO.getAuthor())),
                () -> assertThat(result.getIsbn(), is(libraryBookDTO.getIsbn())),
                () -> assertThat(result.getResume(), is(libraryBookDTO.getResume())),
                () -> assertThat(result.getSpecificIDLoan(), is(libraryBookDTO.getSpecificIDLoan())),
                () -> assertThat(result.getTitle(), is(libraryBookDTO.getTitle())),
                () -> assertThat(result.getYear(), is(libraryBookDTO.getYear()))
        );
    }

    @Test
    @DisplayName("Deve lançar uma exceção")
    void shouldLibraryBookNotFoundException() {

        LibraryBookDTO libraryBookDTO = createLibraryBookDTO()
                .id(ID_EDIT)
                .year(1995)
                .resume("resume edit")
                .isbn("ISBN edit")
                .title("tittle edit")
                .author("Gerson").build();
        Optional<LibraryBook> libraryBook = Optional.of(createLibraryBook().id(ID_EDIT).build());

        when(repository.findById(anyLong())).thenThrow(new LibraryBookNotFoundException());

        assertThrows(LibraryBookNotFoundException.class, () -> editLibraryBookService.editLibraryBook(ID_EDIT, libraryBookDTO));

        verify(repository, times(1)).findById(ID_EDIT);
        verify(repository, times(0)).save(libraryBook.get());
    }
}
