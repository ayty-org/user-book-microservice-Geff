package br.com.phoebus.microservice.biblioteca.userandbook.book;

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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static br.com.phoebus.microservice.biblioteca.userandbook.book.builders.LibraryBookDTOBuilder.createLibraryBookDTO;
import static org.mockito.ArgumentMatchers.anyLong;
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

        List<Long> idsBooks = new ArrayList<>();
        idsBooks.add(1L);
        idsBooks.add(2L);
        LibraryBookDTO libraryBook1 = createLibraryBookDTO().specificIDLoan(ID_LOAN).build();
        LibraryBookDTO libraryBook2 = createLibraryBookDTO().id(2L).specificIDLoan(ID_LOAN).build();
        List<LibraryBookDTO> libraryBookList = Arrays.asList(libraryBook1, libraryBook2);

        when(libraryBookRepository.existsById(anyLong())).thenReturn(true);
        when(getAllBookForSpecificIDLoanService.getAllBooksForSpecificId(anyLong())).thenReturn(libraryBookList);

        changeIDLoanAndBorrowedBooksService.changeStatusAndBorrowed(ID_LOAN, idsBooks);

        ArgumentCaptor<List<LibraryBookDTO>> captorListBook = ArgumentCaptor.forClass(List.class);
        ArgumentCaptor<Long> captorLong = ArgumentCaptor.forClass(Long.class);

        verify(libraryBookRepository, times(1)).existsById(1L);
        verify(libraryBookRepository, times(1)).existsById(2L);

        //estava pensando em como usar o argumentcaptor para fazer as capturas...

    }
}
