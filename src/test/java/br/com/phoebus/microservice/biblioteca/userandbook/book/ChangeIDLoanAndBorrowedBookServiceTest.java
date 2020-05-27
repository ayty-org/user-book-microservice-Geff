package br.com.phoebus.microservice.biblioteca.userandbook.book;

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
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static br.com.phoebus.microservice.biblioteca.userandbook.book.builders.LibraryBookBuilder.createLibraryBook;
import static br.com.phoebus.microservice.biblioteca.userandbook.book.builders.LibraryBookDTOBuilder.createLibraryBookDTO;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertAll;
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
    @DisplayName("Troca o idLoan e o Borrowed para true")
    void shouldChangeIDLoanAndBorrowed() {

        List<Long> idsBooks = Arrays.asList(1L, 2L);

        LibraryBookDTO libraryBookDTO1 = createLibraryBookDTO().build();
        LibraryBookDTO libraryBookDTO2 = createLibraryBookDTO().id(2L).borrowed(true).specificIDLoan(ID_LOAN).build();
        LibraryBookDTO libraryBookDTO3 = createLibraryBookDTO().id(3L).borrowed(true).specificIDLoan(ID_LOAN).build();
        List<LibraryBookDTO> libraryBookList = Arrays.asList(libraryBookDTO2, libraryBookDTO3);

        LibraryBook libraryBook1 = createLibraryBook().id(1L).build();
        LibraryBook libraryBook2 = createLibraryBook().id(2L).borrowed(true).specificIDLoan(ID_LOAN).build();
        LibraryBook libraryBook3 = createLibraryBook().id(3L).borrowed(true).specificIDLoan(ID_LOAN).build();

        when(libraryBookRepository.existsById(anyLong())).thenReturn(true);
        when(getAllBookForSpecificIDLoanService.getAllBooksForSpecificId(anyLong())).thenReturn(libraryBookList);
        when(libraryBookRepository.getOne(eq(1L))).thenReturn(libraryBook1);
        when(libraryBookRepository.getOne(eq(2L))).thenReturn(libraryBook2);
        when(libraryBookRepository.getOne(eq(3L))).thenReturn(libraryBook3);
        changeIDLoanAndBorrowedBooksService.changeStatusAndBorrowed(ID_LOAN, idsBooks);

        ArgumentCaptor<LibraryBook> captorBook1 = ArgumentCaptor.forClass(LibraryBook.class);
        ArgumentCaptor<LibraryBook> captorBook2 = ArgumentCaptor.forClass(LibraryBook.class);
        ArgumentCaptor<Long> captorLong = ArgumentCaptor.forClass(Long.class);

        verify(libraryBookRepository, times(1)).existsById(1L);
        verify(libraryBookRepository, times(1)).existsById(2L);
        verify(libraryBookRepository, times(2)).save(captorBook1.capture());

        LibraryBook result = captorBook1.getValue();

        assertAll("Book",
                () -> assertThat(result.getSpecificIDLoan(), is(ID_LOAN)),
                () -> assertThat(result.isBorrowed(), is(true))
        );

        verify(libraryBookRepository, times(2)).save(captorBook2.capture());

        //estava pensando em como usar o argumentcaptor para fazer as capturas...

    }
}
