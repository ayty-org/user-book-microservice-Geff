package br.com.phoebus.microservice.biblioteca.userandbook.user;

import br.com.phoebus.microservice.biblioteca.userandbook.libraryuser.LibraryUser;
import br.com.phoebus.microservice.biblioteca.userandbook.libraryuser.LibraryUserDTO;
import br.com.phoebus.microservice.biblioteca.userandbook.libraryuser.LibraryUserRepository;
import br.com.phoebus.microservice.biblioteca.userandbook.libraryuser.service.SaveLibraryUserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static br.com.phoebus.microservice.biblioteca.userandbook.user.builders.LibraryUserDTOBuilder.createLibraryUserDTO;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@Tag("Service")
@DisplayName("Valida a funcionalidade do serviço em saçvar im usuário")
public class SaveLibraryUserServiceTest {

    @Mock
    private LibraryUserRepository repository;

    private SaveLibraryUserServiceImpl saveLibraryUserService;

    @BeforeEach
    void setUp() {
        this.saveLibraryUserService = new SaveLibraryUserServiceImpl(repository);
    }

    @Test
    @DisplayName("Deve salvar um usuário")
    void shouldSaveUser() {
        LibraryUserDTO libraryUserDTO = createLibraryUserDTO().id(null).build();

        saveLibraryUserService.saveLibraryUser(libraryUserDTO);

        ArgumentCaptor<LibraryUser> captorUser = ArgumentCaptor.forClass(LibraryUser.class);
        verify(repository).save(captorUser.capture());

        LibraryUser result = captorUser.getValue();

        assertAll("User",
                () -> assertThat(result.getId(), is(libraryUserDTO.getId())),
                () -> assertThat(result.getSpecificIDLoan(), is(libraryUserDTO.getSpecificIDLoan())),
                () -> assertThat(result.getName(), is(libraryUserDTO.getName())),
                () -> assertThat(result.getTelephone(), is(libraryUserDTO.getTelephone())),
                () -> assertThat(result.getAge(), is(libraryUserDTO.getAge()))
        );
    }
}
