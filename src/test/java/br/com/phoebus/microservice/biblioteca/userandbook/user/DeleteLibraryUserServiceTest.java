package br.com.phoebus.microservice.biblioteca.userandbook.user;

import br.com.phoebus.microservice.biblioteca.userandbook.exceptions.LibraryUserNotFoundException;
import br.com.phoebus.microservice.biblioteca.userandbook.libraryuser.LibraryUserRepository;
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

@ExtendWith(MockitoExtension.class)
@Tag("Service")
@DisplayName("Valida a funcionalidade do serviço responsável por deletar um usuário")
public class DeleteLibraryUserServiceTest {

    @Mock
    private LibraryUserRepository repository;

    private DeleteLibraryUserServiceImpl deleteLibraryUserService;

    private final Long ID_DELETE = 1L;

    @BeforeEach
    void setUp() {
        this.deleteLibraryUserService = new DeleteLibraryUserServiceImpl(repository);
    }

    @Test
    @DisplayName("Deve deletar um usuário")
    void shouldDeleteUser() {

        when(repository.existsById(anyLong())).thenReturn(true);

        deleteLibraryUserService.deleteLibraryUser(ID_DELETE);

        verify(repository).deleteById(ID_DELETE);
    }

    @Test
    @DisplayName("Deve lançar uma exceção user not found exception")
    void shouldNotFoundException() {

        when(repository.existsById(anyLong())).thenReturn(false);

        Assertions.assertThrows(LibraryUserNotFoundException.class, () -> deleteLibraryUserService.deleteLibraryUser(ID_DELETE));

        verify(repository, times(0)).deleteById(ID_DELETE);
    }

}
