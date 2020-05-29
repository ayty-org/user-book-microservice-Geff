package br.com.phoebus.microservice.biblioteca.userandbook.book;

import br.com.phoebus.microservice.biblioteca.userandbook.exceptions.LibraryBookNotFoundException;
import br.com.phoebus.microservice.biblioteca.userandbook.librarybook.LibraryBook;
import br.com.phoebus.microservice.biblioteca.userandbook.librarybook.LibraryBookDTO;
import br.com.phoebus.microservice.biblioteca.userandbook.librarybook.LibraryBookRepository;
import br.com.phoebus.microservice.biblioteca.userandbook.librarybook.service.ChangeIDLoanAndBorrowedBooksServiceImpl;
import br.com.phoebus.microservice.biblioteca.userandbook.librarybook.service.GetAllBookForSpecificIDLoanServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static br.com.phoebus.microservice.biblioteca.userandbook.book.builders.LibraryBookBuilder.createLibraryBook;
import static br.com.phoebus.microservice.biblioteca.userandbook.book.builders.LibraryBookDTOBuilder.createLibraryBookDTO;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Tag("Service")
@ExtendWith(MockitoExtension.class)
@DisplayName("Valida a funcionalidade do serviço responsavel por trocar o status e IDLoan")
public class ChangeIDLoanAndBorrowedBookServiceTest {

    @Mock
    private LibraryBookRepository libraryBookRepository;
    @Mock
    private GetAllBookForSpecificIDLoanServiceImpl getAllBookForSpecificIDLoanService;

    private ChangeIDLoanAndBorrowedBooksServiceImpl changeIDLoanAndBorrowedBooksService;

    private final Long ID_LOAN = 1L;

    @BeforeEach
    void setUp() {
        this.changeIDLoanAndBorrowedBooksService = new ChangeIDLoanAndBorrowedBooksServiceImpl(libraryBookRepository, getAllBookForSpecificIDLoanService);
    }

    @Test
    @DisplayName("troca o status de livros que não estão na lista de idsBooks")
    void shouldChangeStatus() {

        List<Long> idsBooks = Arrays.asList(1L, 2L);

        when(libraryBookRepository.existsById(anyLong())).thenReturn(true);

        List<LibraryBookDTO> libraryBookDTOList = Arrays.asList();
        when(getAllBookForSpecificIDLoanService.getAllBooksForSpecificId(anyLong())).thenReturn(libraryBookDTOList);


        LibraryBook libraryBook1 = createLibraryBook().id(1L).build();
        when(libraryBookRepository.getOne(eq(1L))).thenReturn(libraryBook1);
        LibraryBook libraryBook2 = createLibraryBook().id(2L).build();
        when(libraryBookRepository.getOne(eq(2L))).thenReturn(libraryBook2);

        changeIDLoanAndBorrowedBooksService.changeStatusAndBorrowed(ID_LOAN, idsBooks);

        verify(libraryBookRepository, times(2)).existsById(anyLong());
        verify(libraryBookRepository, times(1)).existsById(eq(1L));
        verify(libraryBookRepository, times(1)).existsById(eq(2L));

        verify(getAllBookForSpecificIDLoanService, times(1)).getAllBooksForSpecificId(eq(ID_LOAN));

        verify(libraryBookRepository, times(2)).getOne(anyLong());
        verify(libraryBookRepository, times(1)).getOne(eq(1L));
        verify(libraryBookRepository, times(1)).getOne(eq(2L));

        verify(libraryBookRepository, times(2)).save(any(LibraryBook.class));
        verify(libraryBookRepository, times(1)).save(eq(libraryBook1));
        verify(libraryBookRepository, times(1)).save(eq(libraryBook2));

    }

    @Test
    @DisplayName("Adicionando o Book 3 ao emprestimo")
    void shouldAddBook3OnLoan() {

        List<Long> idsBooks = Arrays.asList(1L, 2L, 3L);

        when(libraryBookRepository.existsById(anyLong())).thenReturn(true);

        LibraryBookDTO libraryBookDTO1 = createLibraryBookDTO().build();
        LibraryBookDTO libraryBookDTO2 = createLibraryBookDTO().id(2L).build();
        List<LibraryBookDTO> libraryBookDTOList = Arrays.asList(libraryBookDTO1, libraryBookDTO2);
        when(getAllBookForSpecificIDLoanService.getAllBooksForSpecificId(anyLong())).thenReturn(libraryBookDTOList);

        //novo livro do emprestimo
        LibraryBook libraryBook3 = createLibraryBook().id(3L).build();
        when(libraryBookRepository.getOne(eq(3L))).thenReturn(libraryBook3);

        changeIDLoanAndBorrowedBooksService.changeStatusAndBorrowed(ID_LOAN, idsBooks);

        verify(libraryBookRepository, times(3)).existsById(anyLong());
        verify(libraryBookRepository, times(1)).existsById(eq(idsBooks.get(0)));
        verify(libraryBookRepository, times(1)).existsById(eq(idsBooks.get(1)));
        verify(libraryBookRepository, times(1)).existsById(eq(idsBooks.get(2)));

        verify(getAllBookForSpecificIDLoanService, times(1)).getAllBooksForSpecificId(eq(ID_LOAN));

        verify(libraryBookRepository, times(1)).getOne(eq(idsBooks.get(2)));
        verify(libraryBookRepository, times(1)).getOne(anyLong());
        verify(libraryBookRepository, times(1)).save(eq(libraryBook3));
        verify(libraryBookRepository, times(1)).save(any(LibraryBook.class));
    }

    @Test
    @DisplayName("Remove o livro 1, deixa o livro 2, e adiciona o livro 3")
    void shouldRemove1Add3() {

        List<Long> idsBooks = Arrays.asList(2L, 3L);

        when(libraryBookRepository.existsById(anyLong())).thenReturn(true);

        LibraryBookDTO libraryBookDTO1 = createLibraryBookDTO().build();
        LibraryBookDTO libraryBookDTO2 = createLibraryBookDTO().id(2L).build();
        List<LibraryBookDTO> libraryBookDTOList = Arrays.asList(libraryBookDTO1, libraryBookDTO2);
        when(getAllBookForSpecificIDLoanService.getAllBooksForSpecificId(anyLong())).thenReturn(libraryBookDTOList);

        //Esses livros fazem parte do emprestimo e não estão na lista
        LibraryBook libraryBook1 = LibraryBook.to(libraryBookDTO1);
        when(libraryBookRepository.getOne(eq(1L))).thenReturn(libraryBook1);

        //novo livro do emprestimo
        LibraryBook libraryBook3 = createLibraryBook().id(3L).build();
        when(libraryBookRepository.getOne(eq(3L))).thenReturn(libraryBook3);

        changeIDLoanAndBorrowedBooksService.changeStatusAndBorrowed(ID_LOAN, idsBooks);

        verify(libraryBookRepository, times(2)).existsById(anyLong());
        verify(libraryBookRepository, times(1)).existsById(eq(idsBooks.get(0)));
        verify(libraryBookRepository, times(1)).existsById(eq(idsBooks.get(1)));

        verify(getAllBookForSpecificIDLoanService, times(1)).getAllBooksForSpecificId(eq(ID_LOAN));

        verify(libraryBookRepository, times(2)).getOne(anyLong());
        verify(libraryBookRepository, times(1)).getOne(eq(libraryBookDTO1.getId()));
        verify(libraryBookRepository, times(1)).getOne(eq(idsBooks.get(1)));

        verify(libraryBookRepository, times(2)).save(any(LibraryBook.class));
        verify(libraryBookRepository, times(1)).save(eq(libraryBook3));
        verify(libraryBookRepository, times(1)).save(eq(libraryBook1));
    }

    @Test
    @DisplayName("Lança uma exceção de que os livros não existem")
    void shouldExceptionBookNotFound() {

        List<Long> idsBooks = Arrays.asList(2L, 3L);

        when(libraryBookRepository.existsById(anyLong())).thenReturn(false);

        assertThrows(LibraryBookNotFoundException.class, () -> changeIDLoanAndBorrowedBooksService.changeStatusAndBorrowed(ID_LOAN, idsBooks));

        verify(libraryBookRepository, times(1)).existsById(anyLong());

        verify(getAllBookForSpecificIDLoanService, times(0)).getAllBooksForSpecificId(anyLong());
        verify(libraryBookRepository, times(0)).getOne(anyLong());
        verify(libraryBookRepository, times(0)).save(any(LibraryBook.class));
    }
}