package br.com.phoebus.microservice.biblioteca.userandbook.book;

import br.com.phoebus.microservice.biblioteca.userandbook.exceptions.LibraryBookAlreadyBorrowedException;
import br.com.phoebus.microservice.biblioteca.userandbook.exceptions.LibraryBookNotFoundException;
import br.com.phoebus.microservice.biblioteca.userandbook.librarybook.LibraryBook;
import br.com.phoebus.microservice.biblioteca.userandbook.librarybook.LibraryBookRepository;
import br.com.phoebus.microservice.biblioteca.userandbook.librarybook.service.VerifyLibraryBooksAvailableForLoanServiceImpl;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Tag("Service")
@ExtendWith(MockitoExtension.class)
@DisplayName("Valida a funcionalidade do serviço em verificar se o livro está disponivel para emprestimo")
public class VerifyLibraryBooksAvailableForLoanServiceTest {

    @Mock
    private LibraryBookRepository repository;

    private VerifyLibraryBooksAvailableForLoanServiceImpl verifyLibraryBooksAvailableForLoanService;

    @BeforeEach
    void setUp() {
        this.verifyLibraryBooksAvailableForLoanService = new VerifyLibraryBooksAvailableForLoanServiceImpl(repository);
    }

    @Test
    @DisplayName("Não deve lançar nenhuma exceção")
    void shouldOk() {
        LibraryBook libraryBook1 = createLibraryBook().id(1L).build();
        LibraryBook libraryBook2 = createLibraryBook().id(2L).build();
        List<Long> idsBooks = Arrays.asList(1L, 2L);

        when(repository.existsById(anyLong())).thenReturn(true);
        when(repository.getOne(eq(1L))).thenReturn(libraryBook1);
        when(repository.getOne(eq(2L))).thenReturn(libraryBook2);

        verifyLibraryBooksAvailableForLoanService.verifyExistLibraryBooks(idsBooks);

        verify(repository, times(1)).existsById(eq(1L));
        verify(repository, times(1)).existsById(eq(2L));
        verify(repository, times(2)).existsById(anyLong());
        verify(repository, times(1)).getOne(eq(1L));
        verify(repository, times(1)).getOne(eq(2L));
        verify(repository, times(2)).getOne(anyLong());

    }

    @Test
    @DisplayName("Lança uma exceção de Book Already Borrowed")
    void shouldExceptionBookAlreadyBorrowed() {
        LibraryBook libraryBook1 = createLibraryBook().specificIDLoan(1L).id(1L).borrowed(false).build();
        LibraryBook libraryBook2 = createLibraryBook().specificIDLoan(1L).id(2L).borrowed(true).build();
        List<Long> idsBooks = Arrays.asList(1L, 2L);

        when(repository.existsById(anyLong())).thenReturn(true);
        when(repository.getOne(eq(1L))).thenReturn(libraryBook1);
        when(repository.getOne(eq(2L))).thenReturn(libraryBook2);

        assertThrows(LibraryBookAlreadyBorrowedException.class, () -> verifyLibraryBooksAvailableForLoanService.verifyExistLibraryBooks(idsBooks));

        verify(repository, times(1)).existsById(eq(1L));
        verify(repository, times(1)).existsById(eq(2L));
        verify(repository, times(2)).existsById(anyLong());
        verify(repository, times(1)).getOne(eq(1L));
        verify(repository, times(1)).getOne(eq(2L));
        verify(repository, times(2)).getOne(anyLong());

    }

    @Test
    @DisplayName("Lança uma exceção de Book Not Found")
    void shouldExceptionBookNotFound() {
        LibraryBook libraryBook1 = createLibraryBook().specificIDLoan(1L).id(1L).borrowed(false).build();
        List<Long> idsBooks = Arrays.asList(1L, 2L);

        when(repository.existsById(eq(1L))).thenReturn(true);
        when(repository.existsById(eq(2L))).thenReturn(false);
        when(repository.getOne(eq(1L))).thenReturn(libraryBook1);

        assertThrows(LibraryBookNotFoundException.class, () -> verifyLibraryBooksAvailableForLoanService.verifyExistLibraryBooks(idsBooks));

        verify(repository, times(1)).existsById(eq(1L));
        verify(repository, times(1)).existsById(eq(2L));
        verify(repository, times(2)).existsById(anyLong());
        verify(repository, times(1)).getOne(eq(1L));
        verify(repository, times(0)).getOne(eq(2L));
        verify(repository, times(1)).getOne(anyLong());
    }
}
