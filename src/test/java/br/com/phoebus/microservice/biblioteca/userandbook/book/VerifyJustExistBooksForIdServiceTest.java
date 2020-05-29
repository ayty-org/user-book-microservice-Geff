package br.com.phoebus.microservice.biblioteca.userandbook.book;

import br.com.phoebus.microservice.biblioteca.userandbook.exceptions.LibraryBookNotFoundException;
import br.com.phoebus.microservice.biblioteca.userandbook.librarybook.LibraryBookRepository;
import br.com.phoebus.microservice.biblioteca.userandbook.librarybook.service.VerifyJustExistBooksForIdServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Tag("Service")
@ExtendWith(MockitoExtension.class)
@DisplayName("Valida a funcionalidade do serviço em verificar a existencia dos livros")
public class VerifyJustExistBooksForIdServiceTest {
    @Mock
    private LibraryBookRepository repository;

    private VerifyJustExistBooksForIdServiceImpl verifyJustExistBooksForIdService;

    @BeforeEach
    void setUp() {
        this.verifyJustExistBooksForIdService = new VerifyJustExistBooksForIdServiceImpl(repository);
    }

    @Test
    @DisplayName("Não deve lançar exceção")
    void shouldOk() {
        List<Long> idsBooks = Arrays.asList(1L, 2L, 3L);

        when(repository.existsById(anyLong())).thenReturn(true);

        verifyJustExistBooksForIdService.verifyJustExistBooksForId(idsBooks);

        verify(repository, times(3)).existsById(anyLong());
        verify(repository, times(1)).existsById(eq(1L));
        verify(repository, times(1)).existsById(eq(2L));
        verify(repository, times(1)).existsById(eq(3L));
    }

    @Test
    @DisplayName("Lança uma exceçao book not found")
    void shouldBookNotFound() {
        List<Long> idsBooks = Arrays.asList(1L, 2L, 3L);

        when(repository.existsById(eq(1L))).thenReturn(true);
        when(repository.existsById(eq(2L))).thenReturn(false);

        assertThrows(LibraryBookNotFoundException.class, () -> verifyJustExistBooksForIdService.verifyJustExistBooksForId(idsBooks));

        verify(repository, times(2)).existsById(anyLong());
        verify(repository, times(1)).existsById(eq(1L));
        verify(repository, times(1)).existsById(eq(2L));
        verify(repository, times(0)).existsById(eq(3L));
    }

    @Test
    @DisplayName("Lança exceção de book not foun com lista de ids vazia")
    void shouldBookNotFoundBySize0() {
        List<Long> idsBooks = Arrays.asList();

        assertThrows(LibraryBookNotFoundException.class, () -> verifyJustExistBooksForIdService.verifyJustExistBooksForId(idsBooks));

        verify(repository, times(0)).existsById(anyLong());
    }
}
