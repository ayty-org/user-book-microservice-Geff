package br.com.phoebus.microservice.biblioteca.userandbook.user;

import br.com.phoebus.microservice.biblioteca.userandbook.libraryuser.LibraryUser;
import br.com.phoebus.microservice.biblioteca.userandbook.libraryuser.LibraryUserDTO;
import br.com.phoebus.microservice.biblioteca.userandbook.libraryuser.LibraryUserRepository;
import br.com.phoebus.microservice.biblioteca.userandbook.libraryuser.service.PageLibratyUserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

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
@DisplayName("Valida a funcionalidade do serviço em paginar os usuários")
public class PageLibraryUserServiceTest {

    @Mock
    private LibraryUserRepository repository;

    private PageLibratyUserServiceImpl pageLibratyUserService;

    @BeforeEach
    void setUp() {
        this.pageLibratyUserService = new PageLibratyUserServiceImpl(repository);
    }

    @Test
    @DisplayName("Deve retornar os usuários por paginação")
    void shouldUserForPage() {

        Pageable pageRequest = PageRequest.of(0, 2, Sort.Direction.ASC, "id");

        LibraryUser libraryUser1 = createLibraryUser().build();
        LibraryUser libraryUser2 = createLibraryUser().id(2L).name("Felipe").build();
        LibraryUser libraryUser3 = createLibraryUser().id(3L).name("Gerson").build();
        List<LibraryUser> libraryUsers = Arrays.asList(libraryUser1,libraryUser2,libraryUser3);
        PageImpl<LibraryUser> libraryUserPage = new PageImpl<>(libraryUsers);

        when(repository.findAll(pageRequest)).thenReturn(libraryUserPage);

        Page<LibraryUserDTO> result = pageLibratyUserService.listPageLibraryUser(0,2);

        assertAll("User",
                () -> assertThat(result.getTotalElements(), is(libraryUserPage.getTotalElements())),
                () -> assertThat(result.getTotalPages(), is(libraryUserPage.getTotalPages())),
                () -> assertThat(result.getContent().size(), is(libraryUserPage.getContent().size())),

                () -> assertThat(result.getContent().get(0).getId(), is(libraryUser1.getId())),
                () -> assertThat(result.getContent().get(0).getName(), is(libraryUser1.getName())),
                () -> assertThat(result.getContent().get(0).getTelephone(), is(libraryUser1.getTelephone())),
                () -> assertThat(result.getContent().get(0).getAge(), is(libraryUser1.getAge())),
                () -> assertThat(result.getContent().get(0).getSpecificIDLoan(), is(libraryUser1.getSpecificIDLoan())),

                () -> assertThat(result.getContent().get(1).getId(), is(libraryUser2.getId())),
                () -> assertThat(result.getContent().get(1).getName(), is(libraryUser2.getName())),
                () -> assertThat(result.getContent().get(1).getTelephone(), is(libraryUser2.getTelephone())),
                () -> assertThat(result.getContent().get(1).getAge(), is(libraryUser2.getAge())),
                () -> assertThat(result.getContent().get(1).getSpecificIDLoan(), is(libraryUser2.getSpecificIDLoan())),

                () -> assertThat(result.getContent().get(2).getId(), is(libraryUser3.getId())),
                () -> assertThat(result.getContent().get(2).getName(), is(libraryUser3.getName())),
                () -> assertThat(result.getContent().get(2).getTelephone(), is(libraryUser3.getTelephone())),
                () -> assertThat(result.getContent().get(2).getAge(), is(libraryUser3.getAge())),
                () -> assertThat(result.getContent().get(2).getSpecificIDLoan(), is(libraryUser3.getSpecificIDLoan()))
        );
        verify(repository, times(1)).findAll(pageRequest);
    }

}
