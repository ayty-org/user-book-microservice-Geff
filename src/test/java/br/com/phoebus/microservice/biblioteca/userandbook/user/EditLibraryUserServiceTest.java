package br.com.phoebus.microservice.biblioteca.userandbook.user;

import br.com.phoebus.microservice.biblioteca.userandbook.exceptions.LibraryUserNotFoundException;
import br.com.phoebus.microservice.biblioteca.userandbook.libraryuser.LibraryUser;
import br.com.phoebus.microservice.biblioteca.userandbook.libraryuser.LibraryUserDTO;
import br.com.phoebus.microservice.biblioteca.userandbook.libraryuser.LibraryUserRepository;
import br.com.phoebus.microservice.biblioteca.userandbook.libraryuser.service.EditLibraryUserServiceImpl;
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
import static br.com.phoebus.microservice.biblioteca.userandbook.user.builders.LibraryUserDTOBuilder.createLibraryUserDTO;
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
@DisplayName("Valida a funcionalidade do serviço responsavel por editar um usuário")
public class EditLibraryUserServiceTest {

    @Mock
    private LibraryUserRepository repository;

    private EditLibraryUserServiceImpl editLibraryUserService;

    private final Long ID_EDIT = 1L;

    @BeforeEach
    void setUp() {
        this.editLibraryUserService = new EditLibraryUserServiceImpl(repository);
    }

    @Test
    @DisplayName("Deve editar um usuário")
    void shouldEditUser() {

        LibraryUserDTO libraryUserDTO = createLibraryUserDTO()
                .id(ID_EDIT)
                .name("Felipe")
                .age(19).build();
        Optional<LibraryUser> libraryUser = Optional.of(createLibraryUser().id(ID_EDIT).build());

        when(repository.findById(anyLong())).thenReturn(libraryUser);

        editLibraryUserService.editLibraryUser(ID_EDIT, libraryUserDTO);

        ArgumentCaptor<LibraryUser> captorUser = ArgumentCaptor.forClass(LibraryUser.class);
        verify(repository, times(1)).findById(ID_EDIT);
        verify(repository, times(1)).save(captorUser.capture());

        LibraryUser result = captorUser.getValue();

        assertAll("User",
                () -> assertThat(result.getAge(), is(libraryUserDTO.getAge())),
                () -> assertThat(result.getId(), is(ID_EDIT)),
                () -> assertThat(result.getName(), is(libraryUserDTO.getName())),
                () -> assertThat(result.getTelephone(), is(libraryUserDTO.getTelephone())),
                () -> assertThat(result.getSpecificIDLoan(), is(libraryUserDTO.getSpecificIDLoan()))

        );
    }

    @Test
    @DisplayName("Deve lançar uma exceção")
    void shouldLibraryUserNotFoundException() {

        LibraryUserDTO libraryUserDTO = createLibraryUserDTO()
                .id(ID_EDIT)
                .name("Felipe")
                .age(19).build();
        Optional<LibraryUser> libraryUser = Optional.of(createLibraryUser().id(ID_EDIT).build());

        when(repository.findById(anyLong())).thenThrow(new LibraryUserNotFoundException());

        assertThrows(LibraryUserNotFoundException.class, () -> editLibraryUserService.editLibraryUser(ID_EDIT, libraryUserDTO));

        verify(repository, times(1)).findById(ID_EDIT);
        verify(repository, times(0)).save(libraryUser.get());
    }
}
