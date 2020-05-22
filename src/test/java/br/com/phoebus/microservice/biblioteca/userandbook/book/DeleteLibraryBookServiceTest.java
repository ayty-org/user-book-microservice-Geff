package br.com.phoebus.microservice.biblioteca.userandbook.book;

import br.com.phoebus.microservice.biblioteca.userandbook.exceptions.LibraryBookNotFoundException;
import br.com.phoebus.microservice.biblioteca.userandbook.librarybook.LibraryBookRepository;
import br.com.phoebus.microservice.biblioteca.userandbook.librarybook.service.DeleteLibraryBookServiceImpl;
import br.com.phoebus.microservice.biblioteca.userandbook.libraryuser.service.DeleteLibraryUserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Tag("Service")
@ExtendWith(MockitoExtension.class)
@DisplayName("Valida a funcionalidade do serviços responsavel por deletar um livro")
public class DeleteLibraryBookServiceTest {

    @Mock
    private LibraryBookRepository repository;

    private DeleteLibraryBookServiceImpl deleteLibraryBookService;

    private final Long ID_DELETE = 1L;

    @BeforeEach
    void setUp() {
        this.deleteLibraryBookService = new DeleteLibraryBookServiceImpl(repository);
    }

    @Test
    @DisplayName("Deve deletar um livro")
    void shouldDeleteBook() {

        when(repository.existsById(anyLong())).thenReturn(true);

        deleteLibraryBookService.deleteLibraryBook(ID_DELETE);

        verify(repository, times(1)).deleteById(ID_DELETE);
    }

    @Test
    @DisplayName("Deve lançar uma exceção de book not found exception")
    void shouldNotFoundException() {

        when(repository.existsById(anyLong())).thenReturn(false);

        Assertions.assertThrows(LibraryBookNotFoundException.class, () -> deleteLibraryBookService.deleteLibraryBook(ID_DELETE));

        verify(repository, times(0)).deleteById(ID_DELETE);
    }
}
