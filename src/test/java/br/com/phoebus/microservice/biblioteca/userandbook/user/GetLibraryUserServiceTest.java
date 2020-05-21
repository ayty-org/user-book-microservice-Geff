package br.com.phoebus.microservice.biblioteca.userandbook.user;

import br.com.phoebus.microservice.biblioteca.userandbook.exceptions.LibraryUserNotFoundException;
import br.com.phoebus.microservice.biblioteca.userandbook.libraryuser.LibraryUser;
import br.com.phoebus.microservice.biblioteca.userandbook.libraryuser.LibraryUserDTO;
import br.com.phoebus.microservice.biblioteca.userandbook.libraryuser.LibraryUserRepository;
import br.com.phoebus.microservice.biblioteca.userandbook.libraryuser.service.GetLibraryUserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static br.com.phoebus.microservice.biblioteca.userandbook.user.builders.LibraryUserBuilder.createLibraryUser;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Tag("Service")
@DisplayName("Valida a funcionalidade do serviço em pegar um usuário")
public class GetLibraryUserServiceTest {

    @Mock
    private LibraryUserRepository repository;

    private GetLibraryUserServiceImpl getLibraryUserService;

    private final Long ID_GET = 1L;

    @BeforeEach
    void setUp() {
        this.getLibraryUserService = new GetLibraryUserServiceImpl(repository);
    }

    @Test
    @DisplayName("Deve buscar um usuário")
    void shouldGetUser() {

        Optional<LibraryUser> libraryUser = Optional.of(createLibraryUser().id(ID_GET).build());
        when(repository.findById(anyLong())).thenReturn(libraryUser);

        LibraryUserDTO result = this.getLibraryUserService.getLibraryUserForID(ID_GET);

        assertAll("User",
                () -> assertThat(result.getId(), is(libraryUser.get().getId())),
                () -> assertThat(result.getAge(), is(libraryUser.get().getAge())),
                () -> assertThat(result.getName(), is(libraryUser.get().getName())),
                () -> assertThat(result.getTelephone(), is(libraryUser.get().getTelephone())),
                () -> assertThat(result.getSpecificIDLoan(), is(libraryUser.get().getSpecificIDLoan()))
        );
        verify(repository, times(1)).findById(ID_GET);
    }

    @Test
    @DisplayName("Deve lançar uma exceção")
    void shouldLibraryUserNotFoundException() {

        when(repository.findById(anyLong())).thenThrow(new LibraryUserNotFoundException());

        assertThrows(LibraryUserNotFoundException.class, () -> getLibraryUserService.getLibraryUserForID(ID_GET));

        verify(repository, times(1)).findById(ID_GET);
    }
}
