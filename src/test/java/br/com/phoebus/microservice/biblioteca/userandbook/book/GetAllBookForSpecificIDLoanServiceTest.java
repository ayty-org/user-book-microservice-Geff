package br.com.phoebus.microservice.biblioteca.userandbook.book;

import br.com.phoebus.microservice.biblioteca.userandbook.librarybook.LibraryBook;
import br.com.phoebus.microservice.biblioteca.userandbook.librarybook.LibraryBookDTO;
import br.com.phoebus.microservice.biblioteca.userandbook.librarybook.LibraryBookRepository;
import br.com.phoebus.microservice.biblioteca.userandbook.librarybook.service.GetAllBookForSpecificIDLoanServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static br.com.phoebus.microservice.biblioteca.userandbook.book.builders.LibraryBookBuilder.createLibraryBook;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Tag("Service")
@ExtendWith(MockitoExtension.class)
@DisplayName("Valida a funcionalidade do serviço em pegar todos os livro por um specific ID ")
public class GetAllBookForSpecificIDLoanServiceTest {

    @Mock
    private LibraryBookRepository repository;

    private GetAllBookForSpecificIDLoanServiceImpl getAllBookForSpecificIDLoanService;

    private final Long ID_LOAN = 1L;

    @BeforeEach
    void setUp() {
        this.getAllBookForSpecificIDLoanService = new GetAllBookForSpecificIDLoanServiceImpl(repository);
    }

    @Test
    @DisplayName("Deve retornar uma lista de livros com o ID de emprestimo")
    void shouldReturnListAllBookWithIDLoan() {
        LibraryBook libraryBook1 = createLibraryBook().id(1L).specificIDLoan(ID_LOAN).borrowed(true).build();
        LibraryBook libraryBook2 = createLibraryBook().id(2L).specificIDLoan(ID_LOAN).borrowed(true).build();
        List<LibraryBook> libraryBookList = Arrays.asList(libraryBook1,libraryBook2);

        when(repository.findByspecificIDLoan(anyLong())).thenReturn(libraryBookList);

        List<LibraryBookDTO> result = getAllBookForSpecificIDLoanService.getAllBooksForSpecificId(ID_LOAN);

        assertAll("Book",
                () -> assertThat(result.size(), is(libraryBookList.size())),
                () -> assertThat(result.get(0).getId(), is(libraryBook1.getId())),
                () -> assertThat(result.get(0).getAuthor(), is(libraryBook1.getAuthor())),
                () -> assertThat(result.get(0).getIsbn(), is(libraryBook1.getIsbn())),
                () -> assertThat(result.get(0).getTitle(), is(libraryBook1.getTitle())),
                () -> assertThat(result.get(0).isBorrowed(), is(libraryBook1.isBorrowed())),
                () -> assertThat(result.get(0).getResume(), is(libraryBook1.getResume())),
                () -> assertThat(result.get(0).getSpecificIDLoan(), is(libraryBook1.getSpecificIDLoan())),
                () -> assertThat(result.get(0).getYear(), is(libraryBook1.getYear())),

                () -> assertThat(result.get(1).getId(), is(libraryBook2.getId())),
                () -> assertThat(result.get(1).getAuthor(), is(libraryBook2.getAuthor())),
                () -> assertThat(result.get(1).getIsbn(), is(libraryBook2.getIsbn())),
                () -> assertThat(result.get(1).getTitle(), is(libraryBook2.getTitle())),
                () -> assertThat(result.get(1).isBorrowed(), is(libraryBook2.isBorrowed())),
                () -> assertThat(result.get(1).getResume(), is(libraryBook2.getResume())),
                () -> assertThat(result.get(1).getSpecificIDLoan(), is(libraryBook2.getSpecificIDLoan())),
                () -> assertThat(result.get(1).getYear(), is(libraryBook2.getYear()))
        );

        verify(repository, times(1)).findByspecificIDLoan(ID_LOAN);
    }
}
