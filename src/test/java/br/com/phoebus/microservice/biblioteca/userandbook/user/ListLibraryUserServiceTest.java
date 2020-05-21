package br.com.phoebus.microservice.biblioteca.userandbook.user;

import br.com.phoebus.microservice.biblioteca.userandbook.libraryuser.LibraryUser;
import br.com.phoebus.microservice.biblioteca.userandbook.libraryuser.LibraryUserDTO;
import br.com.phoebus.microservice.biblioteca.userandbook.libraryuser.LibraryUserRepository;
import br.com.phoebus.microservice.biblioteca.userandbook.libraryuser.service.ListLibraryUserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static br.com.phoebus.microservice.biblioteca.userandbook.user.builders.LibraryUserBuilder.createLibraryUser;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Tag("Service")
@DisplayName("Valida a funcionalidade do serviço responsável por buscar um usuário")
public class ListLibraryUserServiceTest {

    @Mock
    private LibraryUserRepository repository;

    private ListLibraryUserServiceImpl listLibraryUserService;

    @BeforeEach
    void setUp() {
        this.listLibraryUserService = new ListLibraryUserServiceImpl(repository);
    }

    @Test
    @DisplayName("Deve listar os usuários")
    void shouldListUsers() {
        LibraryUser libraryUser1 = createLibraryUser().build();
        LibraryUser libraryUser2 = createLibraryUser().id(2L).name("Felipe").build();
        LibraryUser libraryUser3 = createLibraryUser().id(3L).name("Gerson").build();
        List<LibraryUser> libraryUsers = Arrays.asList(libraryUser1,libraryUser2,libraryUser3);

        when(repository.findAll()).thenReturn(libraryUsers);

        List<LibraryUserDTO> result = listLibraryUserService.listLibraryUsers();

        assertAll("Users",
                () -> assertThat(result.size(), is(libraryUsers.size())),

                () -> assertThat(result.get(0).getId(), is(libraryUser1.getId())),
                () -> assertThat(result.get(0).getSpecificIDLoan(), is(libraryUser1.getSpecificIDLoan())),
                () -> assertThat(result.get(0).getAge(), is(libraryUser1.getAge())),
                () -> assertThat(result.get(0).getTelephone(), is(libraryUser1.getTelephone())),
                () -> assertThat(result.get(0).getName(), is(libraryUser1.getName())),

                () -> assertThat(result.get(1).getId(), is(libraryUser2.getId())),
                () -> assertThat(result.get(1).getSpecificIDLoan(), is(libraryUser2.getSpecificIDLoan())),
                () -> assertThat(result.get(1).getAge(), is(libraryUser2.getAge())),
                () -> assertThat(result.get(1).getTelephone(), is(libraryUser2.getTelephone())),
                () -> assertThat(result.get(1).getName(), is(libraryUser2.getName())),

                () -> assertThat(result.get(2).getId(), is(libraryUser3.getId())),
                () -> assertThat(result.get(2).getSpecificIDLoan(), is(libraryUser3.getSpecificIDLoan())),
                () -> assertThat(result.get(2).getAge(), is(libraryUser3.getAge())),
                () -> assertThat(result.get(2).getTelephone(), is(libraryUser3.getTelephone())),
                () -> assertThat(result.get(2).getName(), is(libraryUser3.getName()))
        );
        verify(repository, times(1)).findAll();
    }
}