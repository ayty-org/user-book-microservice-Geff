package br.com.phoebus.microservice.biblioteca.userandbook.user;

import br.com.phoebus.microservice.biblioteca.userandbook.exceptions.LibraryUserNotFoundException;
import br.com.phoebus.microservice.biblioteca.userandbook.libraryuser.LibraryUser;
import br.com.phoebus.microservice.biblioteca.userandbook.libraryuser.LibraryUserRepository;
import br.com.phoebus.microservice.biblioteca.userandbook.libraryuser.service.EditSpecificIdLibraryUserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
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
@DisplayName("Valida a funcionalidade do serviço em alterar o IDSpecificLoan do Usuário")
public class EditSpecificIdLibraryUserServiceTest {

    @Mock
    private LibraryUserRepository repository;

    private EditSpecificIdLibraryUserServiceImpl editSpecificIdLibraryUserService;

    private final Long ID_EDIT = 1L;
    private final String ID_SECIFIC_LOAN = "0001";

    @BeforeEach
    void setUp() {
        this.editSpecificIdLibraryUserService = new EditSpecificIdLibraryUserServiceImpl(repository);
    }

    @Test
    @DisplayName("Edita o specificIdLoan do usuário")
    void shouldEditSpecificIdLoan() {
        Optional<LibraryUser> libraryUser = Optional.of(createLibraryUser().build());

        when(repository.findById(anyLong())).thenReturn(libraryUser);

        editSpecificIdLibraryUserService.editSpecifIdLibraryUser(ID_EDIT, ID_SECIFIC_LOAN);

        ArgumentCaptor<LibraryUser> captorUser = ArgumentCaptor.forClass(LibraryUser.class);
        verify(repository, times(1)).save(captorUser.capture());

        LibraryUser result = captorUser.getValue();

        assertAll("User",
                () -> assertThat(result.getSpecificIDLoan(), is(libraryUser.get().getSpecificIDLoan())),
                () -> assertThat(result.getId(), is(libraryUser.get().getId())),
                () -> assertThat(result.getAge(), is(libraryUser.get().getAge())),
                () -> assertThat(result.getName(), is(libraryUser.get().getName())),
                () -> assertThat(result.getTelephone(), is(libraryUser.get().getTelephone()))
        );
    }

    @Test
    @DisplayName("Deve lançar uma exceção")
    void shouldLibraryUserNotFoundException() {

        Optional<LibraryUser> libraryUser = Optional.of(createLibraryUser().build());

        when(repository.findById(anyLong())).thenThrow(new LibraryUserNotFoundException());

        assertThrows(LibraryUserNotFoundException.class, () -> editSpecificIdLibraryUserService.editSpecifIdLibraryUser(ID_EDIT, ID_SECIFIC_LOAN));

        verify(repository, times(1)).findById(ID_EDIT);
        verify(repository, times(0)).save(libraryUser.get());
    }
}