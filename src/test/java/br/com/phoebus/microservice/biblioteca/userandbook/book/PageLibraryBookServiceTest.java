package br.com.phoebus.microservice.biblioteca.userandbook.book;

import br.com.phoebus.microservice.biblioteca.userandbook.librarybook.LibraryBook;
import br.com.phoebus.microservice.biblioteca.userandbook.librarybook.LibraryBookDTO;
import br.com.phoebus.microservice.biblioteca.userandbook.librarybook.LibraryBookRepository;
import br.com.phoebus.microservice.biblioteca.userandbook.librarybook.service.PageLibraryBookServiceImpl;
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

import static br.com.phoebus.microservice.biblioteca.userandbook.book.builders.LibraryBookBuilder.createLibraryBook;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Tag("Service")
@ExtendWith(MockitoExtension.class)
@DisplayName("Valida a funcionalidade do serviço em paginar os livros")
public class PageLibraryBookServiceTest {

    @Mock
    private LibraryBookRepository repository;

    private PageLibraryBookServiceImpl pageLibraryBookService;

    @BeforeEach
    void setUp() {
        this.pageLibraryBookService = new PageLibraryBookServiceImpl(repository);
    }

    @Test
    @DisplayName("Deve retornar os livros por paginação")
    void shouldUserForPage() {
        Pageable pageRequest = PageRequest.of(0, 2, Sort.Direction.ASC, "id");

        LibraryBook libraryBook1 = createLibraryBook().build();
        LibraryBook libraryBook2 = createLibraryBook().id(2L).build();
        LibraryBook libraryBook3 = createLibraryBook().id(3L).build();
        List<LibraryBook> libraryBookList = Arrays.asList(libraryBook1, libraryBook2, libraryBook3);
        PageImpl<LibraryBook> libraryBookPage = new PageImpl<>(libraryBookList);

        when(repository.findAll(pageRequest)).thenReturn(libraryBookPage);

        Page<LibraryBookDTO> result = pageLibraryBookService.listPageLibraryBooks(0, 2);

        assertAll("Book",
                () -> assertThat(result.getTotalElements(), is(libraryBookPage.getTotalElements())),
                () -> assertThat(result.getTotalPages(), is(libraryBookPage.getTotalPages())),
                () -> assertThat(result.getContent().size(), is(libraryBookPage.getContent().size())),

                () -> assertThat(result.getContent().get(0).getId(), is(libraryBookPage.getContent().get(0).getId())),
                () -> assertThat(result.getContent().get(0).getYear(), is(libraryBookPage.getContent().get(0).getYear())),
                () -> assertThat(result.getContent().get(0).getResume(), is(libraryBookPage.getContent().get(0).getResume())),
                () -> assertThat(result.getContent().get(0).getTitle(), is(libraryBookPage.getContent().get(0).getTitle())),
                () -> assertThat(result.getContent().get(0).getIsbn(), is(libraryBookPage.getContent().get(0).getIsbn())),
                () -> assertThat(result.getContent().get(0).getAuthor(), is(libraryBookPage.getContent().get(0).getAuthor())),
                () -> assertThat(result.getContent().get(0).getSpecificIDLoan(), is(libraryBookPage.getContent().get(0).getSpecificIDLoan())),
                () -> assertThat(result.getContent().get(0).isBorrowed(), is(libraryBookPage.getContent().get(0).isBorrowed())),

                () -> assertThat(result.getContent().get(1).getId(), is(libraryBookPage.getContent().get(1).getId())),
                () -> assertThat(result.getContent().get(1).getYear(), is(libraryBookPage.getContent().get(1).getYear())),
                () -> assertThat(result.getContent().get(1).getResume(), is(libraryBookPage.getContent().get(1).getResume())),
                () -> assertThat(result.getContent().get(1).getTitle(), is(libraryBookPage.getContent().get(1).getTitle())),
                () -> assertThat(result.getContent().get(1).getIsbn(), is(libraryBookPage.getContent().get(1).getIsbn())),
                () -> assertThat(result.getContent().get(1).getAuthor(), is(libraryBookPage.getContent().get(1).getAuthor())),
                () -> assertThat(result.getContent().get(1).getSpecificIDLoan(), is(libraryBookPage.getContent().get(1).getSpecificIDLoan())),
                () -> assertThat(result.getContent().get(1).isBorrowed(), is(libraryBookPage.getContent().get(1).isBorrowed())),

                () -> assertThat(result.getContent().get(2).getId(), is(libraryBookPage.getContent().get(2).getId())),
                () -> assertThat(result.getContent().get(2).getYear(), is(libraryBookPage.getContent().get(2).getYear())),
                () -> assertThat(result.getContent().get(2).getResume(), is(libraryBookPage.getContent().get(2).getResume())),
                () -> assertThat(result.getContent().get(2).getTitle(), is(libraryBookPage.getContent().get(2).getTitle())),
                () -> assertThat(result.getContent().get(2).getIsbn(), is(libraryBookPage.getContent().get(2).getIsbn())),
                () -> assertThat(result.getContent().get(2).getAuthor(), is(libraryBookPage.getContent().get(2).getAuthor())),
                () -> assertThat(result.getContent().get(2).getSpecificIDLoan(), is(libraryBookPage.getContent().get(2).getSpecificIDLoan())),
                () -> assertThat(result.getContent().get(2).isBorrowed(), is(libraryBookPage.getContent().get(2).isBorrowed()))
        );
        verify(repository, times(1)).findAll(pageRequest);
    }
}
