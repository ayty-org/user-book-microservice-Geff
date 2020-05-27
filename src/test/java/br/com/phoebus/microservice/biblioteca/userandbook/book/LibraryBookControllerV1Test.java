package br.com.phoebus.microservice.biblioteca.userandbook.book;

import br.com.phoebus.microservice.biblioteca.userandbook.exceptions.LibraryBookNotFoundException;
import br.com.phoebus.microservice.biblioteca.userandbook.librarybook.LibraryBookDTO;
import br.com.phoebus.microservice.biblioteca.userandbook.librarybook.service.ChangeIDLoanAndBorrowedBooksService;
import br.com.phoebus.microservice.biblioteca.userandbook.librarybook.service.DeleteLibraryBookService;
import br.com.phoebus.microservice.biblioteca.userandbook.librarybook.service.EditLibraryBookService;
import br.com.phoebus.microservice.biblioteca.userandbook.librarybook.service.GetAllBookForSpecificIDLoanService;
import br.com.phoebus.microservice.biblioteca.userandbook.librarybook.service.GetLibraryBookService;
import br.com.phoebus.microservice.biblioteca.userandbook.librarybook.service.ListLibraryBookService;
import br.com.phoebus.microservice.biblioteca.userandbook.librarybook.service.PageLibraryBookService;
import br.com.phoebus.microservice.biblioteca.userandbook.librarybook.service.SaveLibraryBookService;
import br.com.phoebus.microservice.biblioteca.userandbook.librarybook.service.VerifyJustExistBooksForIdService;
import br.com.phoebus.microservice.biblioteca.userandbook.librarybook.service.VerifyLibraryBooksAvailableForLoanService;
import br.com.phoebus.microservice.biblioteca.userandbook.librarybook.v1.LibraryBookControllerV1;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.List;

import static br.com.phoebus.microservice.biblioteca.userandbook.book.builders.LibraryBookDTOBuilder.createLibraryBookDTO;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Tag("Controller")
@ExtendWith(SpringExtension.class)
@WebMvcTest(LibraryBookControllerV1.class)
@DisplayName("Valida a funcionalidade do controlador do livro")
public class LibraryBookControllerV1Test {

    private final String URL_BOOK = "/v1/libraryBook";
    private final String CONT_TYPE = "application/json";
    private final Long ID_BOOK = 1L;
    private final Long ID_LOAN = 1L;
    private final String UTF8 = "utf-8";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ChangeIDLoanAndBorrowedBooksService changeIDLoanAndBorrowedBooksService;
    @MockBean
    private DeleteLibraryBookService deleteLibraryBookService;
    @MockBean
    private EditLibraryBookService editLibraryBookService;
    @MockBean
    private GetAllBookForSpecificIDLoanService getAllBookForSpecificIDLoanService;
    @MockBean
    private GetLibraryBookService getLibraryBookService;
    @MockBean
    private ListLibraryBookService listLibraryBookService;
    @MockBean
    private PageLibraryBookService pageLibraryBookService;
    @MockBean
    private SaveLibraryBookService saveLibraryBookService;
    @MockBean
    private VerifyJustExistBooksForIdService verifyJustExistBooksForIdService;
    @MockBean
    private VerifyLibraryBooksAvailableForLoanService verifyLibraryBooksAvailableForLoanService;

    @Test
    @DisplayName("Altera o IDLoan e Borrowed")
    void shouldChangeIDLoanAndBorrowed() throws Exception {

        List<Long> idsBooks = Arrays.asList(1L, 2L);

        mockMvc.perform(put(URL_BOOK + "/changeStatus/{id}", ID_LOAN)
                .contentType(CONT_TYPE)
                .characterEncoding(UTF8)
                .param("idsBooks", "1, 2"))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(changeIDLoanAndBorrowedBooksService, times(1))
                .changeStatusAndBorrowed(ID_LOAN, idsBooks);
    }

    @Test
    @DisplayName("Deleta um livro")
    void shouldDeleteBook() throws Exception {

        mockMvc.perform(delete(URL_BOOK + "/{id}", ID_BOOK)
                .contentType(CONT_TYPE)
                .characterEncoding(UTF8))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(deleteLibraryBookService, times(1)).deleteLibraryBook(ID_BOOK);
    }

    @Test
    @DisplayName("Edita um livro")
    void shouldEditBook() throws Exception {

        LibraryBookDTO libraryBookDTO = createLibraryBookDTO().build();

        mockMvc.perform(put(URL_BOOK + "/{id}", ID_BOOK)
                .contentType(CONT_TYPE)
                .characterEncoding(UTF8)
                .content(objectMapper.writeValueAsString(libraryBookDTO)))
                .andDo(print())
                .andExpect(status().isNoContent());

        ArgumentCaptor<LibraryBookDTO> captorLibraryBookDTO = ArgumentCaptor.forClass(LibraryBookDTO.class);
        ArgumentCaptor<Long> captorLong = ArgumentCaptor.forClass(Long.class);
        verify(editLibraryBookService, times(1)).editLibraryBook(captorLong.capture(), captorLibraryBookDTO.capture());
        LibraryBookDTO result = captorLibraryBookDTO.getValue();

        assertAll("Book Controller",
                () -> assertThat(result.getId(), is(libraryBookDTO.getId())),
                () -> assertThat(result.getAuthor(), is(libraryBookDTO.getAuthor())),
                () -> assertThat(result.getTitle(), is(libraryBookDTO.getTitle())),
                () -> assertThat(result.getSpecificIDLoan(), is(libraryBookDTO.getSpecificIDLoan())),
                () -> assertThat(result.getIsbn(), is(libraryBookDTO.getIsbn())),
                () -> assertThat(result.getYear(), is(libraryBookDTO.getYear())),
                () -> assertThat(result.getResume(), is(libraryBookDTO.getResume())),
                () -> assertThat(result.isBorrowed(), is(libraryBookDTO.isBorrowed()))
        );
    }

    @Test
    @DisplayName("Retorna um status de BadRequest pelo ano")
    void shouldExceptionOnEditBookForYear() throws Exception {
        LibraryBookDTO libraryBookDTO = createLibraryBookDTO().year(100).build();

        mockMvc.perform(put(URL_BOOK + "/{id}", ID_BOOK)
                .contentType(CONT_TYPE)
                .characterEncoding(UTF8)
                .content(objectMapper.writeValueAsString(libraryBookDTO)))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(editLibraryBookService, times(0)).editLibraryBook(ID_BOOK, libraryBookDTO);
    }

    @Test
    @DisplayName("Retorna todos os livros de um emprestimo")
    void shouldListBooksForIDLoan() throws Exception {
        LibraryBookDTO libraryBookDTO1 = createLibraryBookDTO().borrowed(true).specificIDLoan(ID_LOAN).build();
        LibraryBookDTO libraryBookDTO2 = createLibraryBookDTO().id(2L).borrowed(true).specificIDLoan(ID_LOAN).build();
        LibraryBookDTO libraryBookDTO3 = createLibraryBookDTO().id(3L).borrowed(true).specificIDLoan(ID_LOAN).build();
        List<LibraryBookDTO> libraryBookDTOList = Arrays.asList(libraryBookDTO1, libraryBookDTO2, libraryBookDTO3);

        when(getAllBookForSpecificIDLoanService.getAllBooksForSpecificId(anyLong())).thenReturn(libraryBookDTOList);

        MvcResult mvcResult = mockMvc.perform(get(URL_BOOK + "/getAllLoanBook/{id}", ID_LOAN)
                .contentType(CONT_TYPE)
                .characterEncoding(UTF8))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String resultResponseBody = mvcResult.getResponse().getContentAsString();

        assertThat(objectMapper.writeValueAsString(libraryBookDTOList), is(resultResponseBody));
        verify(getAllBookForSpecificIDLoanService, times(1)).getAllBooksForSpecificId(ID_LOAN);
    }

    @Test
    @DisplayName("Retorna um livro")
    void shouldGetBook() throws Exception {
        LibraryBookDTO libraryBookDTO = createLibraryBookDTO().id(ID_BOOK).build();
        when(getLibraryBookService.getLibraryBookForID(anyLong())).thenReturn(libraryBookDTO);

        MvcResult mvcResult = mockMvc.perform(get(URL_BOOK + "/{id}", ID_BOOK)
                .characterEncoding(UTF8)
                .contentType(CONT_TYPE))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String resultResponseBody = mvcResult.getResponse().getContentAsString();

        assertThat(objectMapper.writeValueAsString(libraryBookDTO), is(resultResponseBody));

        verify(getLibraryBookService, times(1)).getLibraryBookForID(ID_BOOK);
    }

    @Test
    @DisplayName("Lança uma exceção de livro não encontrado")
    void shouldBookNotFoundOnGet() throws Exception {

        when(getLibraryBookService.getLibraryBookForID(ID_BOOK)).thenThrow(new LibraryBookNotFoundException());
        mockMvc.perform(get(URL_BOOK + "/{id}", ID_BOOK)
                .contentType(CONT_TYPE)
                .characterEncoding(UTF8))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Lista todos os livros")
    void shouldListAllBooks() throws Exception {
        LibraryBookDTO libraryBookDTO1 = createLibraryBookDTO().borrowed(true).specificIDLoan(ID_LOAN).build();
        LibraryBookDTO libraryBookDTO2 = createLibraryBookDTO().id(2L).borrowed(true).specificIDLoan(ID_LOAN).build();
        LibraryBookDTO libraryBookDTO3 = createLibraryBookDTO().id(3L).borrowed(true).specificIDLoan(ID_LOAN).build();
        List<LibraryBookDTO> libraryBookDTOList = Arrays.asList(libraryBookDTO1, libraryBookDTO2, libraryBookDTO3);

        when(listLibraryBookService.listLibraryBooks()).thenReturn(libraryBookDTOList);

        MvcResult mvcResult = mockMvc.perform(get(URL_BOOK)
                .contentType(CONT_TYPE)
                .characterEncoding(UTF8))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String resultResponseBody = mvcResult.getResponse().getContentAsString();

        assertThat(objectMapper.writeValueAsString(libraryBookDTOList), is(resultResponseBody));

        verify(listLibraryBookService, times(1)).listLibraryBooks();
    }

    @Test
    @DisplayName("Traz a pagina dos livros")
    void shouldPageBooks() throws Exception {
        LibraryBookDTO libraryBookDTO1 = createLibraryBookDTO().borrowed(true).specificIDLoan(ID_LOAN).build();
        LibraryBookDTO libraryBookDTO2 = createLibraryBookDTO().id(2L).borrowed(true).specificIDLoan(ID_LOAN).build();
        LibraryBookDTO libraryBookDTO3 = createLibraryBookDTO().id(3L).borrowed(true).specificIDLoan(ID_LOAN).build();
        LibraryBookDTO libraryBookDTO4 = createLibraryBookDTO().id(4L).borrowed(true).specificIDLoan(ID_LOAN).build();
        LibraryBookDTO libraryBookDTO5 = createLibraryBookDTO().id(5L).borrowed(true).specificIDLoan(ID_LOAN).build();
        List<LibraryBookDTO> libraryBookDTOList = Arrays.asList(libraryBookDTO1, libraryBookDTO2, libraryBookDTO3, libraryBookDTO4, libraryBookDTO5);

        PageRequest pageRequest = PageRequest.of(1, 2);

        Page<LibraryBookDTO> libraryBookDTOPage = new PageImpl<>(libraryBookDTOList, pageRequest, libraryBookDTOList.size());

        when(pageLibraryBookService.listPageLibraryBooks(anyInt(), anyInt())).thenReturn(libraryBookDTOPage);

        MvcResult mvcResult = mockMvc.perform(get(URL_BOOK)
                .contentType(CONT_TYPE)
                .characterEncoding(UTF8)
                .param("page", "1")
                .param("size", "2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String resultResponsyBody = mvcResult.getResponse().getContentAsString();

        verify(pageLibraryBookService, times(1)).listPageLibraryBooks(1, 2);

        assertThat(objectMapper.writeValueAsString(libraryBookDTOPage), is(resultResponsyBody));
    }

    @Test
    @DisplayName("Salva um livro")
    void shouldSaveBook() throws Exception {

        LibraryBookDTO libraryBookDTO = createLibraryBookDTO().id(null).build();

        mockMvc.perform(post(URL_BOOK)
                .characterEncoding(UTF8)
                .contentType(CONT_TYPE)
                .content(objectMapper.writeValueAsString(libraryBookDTO)))
                .andDo(print())
                .andExpect(status().isCreated());

        ArgumentCaptor<LibraryBookDTO> captorLibraryBookDTO = ArgumentCaptor.forClass(LibraryBookDTO.class);
        verify(saveLibraryBookService, times(1)).saveLibraryBook(captorLibraryBookDTO.capture());
        LibraryBookDTO result = captorLibraryBookDTO.getValue();

        assertAll("Book Controller",
                () -> assertThat(result.getId(), is(libraryBookDTO.getId())),
                () -> assertThat(result.getResume(), is(libraryBookDTO.getResume())),
                () -> assertThat(result.getYear(), is(libraryBookDTO.getYear())),
                () -> assertThat(result.getIsbn(), is(libraryBookDTO.getIsbn())),
                () -> assertThat(result.getSpecificIDLoan(), is(libraryBookDTO.getSpecificIDLoan())),
                () -> assertThat(result.getTitle(), is(libraryBookDTO.getTitle())),
                () -> assertThat(result.getAuthor(), is(libraryBookDTO.getAuthor())),
                () -> assertThat(result.isBorrowed(), is(libraryBookDTO.isBorrowed()))
        );
    }

    @Test
    @DisplayName("Lança uma exceção pelo ano durante a criação")
    void shouldBadRequestForAge() throws Exception {

        LibraryBookDTO libraryBookDTO = createLibraryBookDTO().id(null).year(200).build();

        mockMvc.perform(post(URL_BOOK)
                .characterEncoding(UTF8)
                .contentType(CONT_TYPE)
                .content(objectMapper.writeValueAsString(libraryBookDTO)))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(saveLibraryBookService, times(0)).saveLibraryBook(any(LibraryBookDTO.class));
    }

    @Test
    @DisplayName("Lança uma exceção pelo autor durante a criação")
    void shouldBadRequestForAuthor() throws Exception {

        LibraryBookDTO libraryBookDTO = createLibraryBookDTO().id(null).author("").build();

        mockMvc.perform(post(URL_BOOK)
                .characterEncoding(UTF8)
                .contentType(CONT_TYPE)
                .content(objectMapper.writeValueAsString(libraryBookDTO)))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(saveLibraryBookService, times(0)).saveLibraryBook(any(LibraryBookDTO.class));
    }

    @Test
    @DisplayName("Verifica se existem os livros pelo ID")
    void shouldVerifyExistBooks() throws Exception {
        List<Long> idsBooks = Arrays.asList(1L, 2L);

        mockMvc.perform(get(URL_BOOK + "/verifyJustExist")
                .contentType(CONT_TYPE)
                .characterEncoding(UTF8)
                .param("idsBooks", "1, 2"))
                .andDo(print())
                .andExpect(status().isOk());

        verify(verifyJustExistBooksForIdService, times(1)).verifyJustExistBooksForId(idsBooks);
    }

    @Test
    @DisplayName("Verifica se os livros estão prontos para serem emprestado")
    void shouldVerifyBooksAvailable() throws Exception {
        List<Long> idsBooks = Arrays.asList(1L, 2L);

        mockMvc.perform(get(URL_BOOK + "/verifyBooks")
                .contentType(CONT_TYPE)
                .characterEncoding(UTF8)
                .param("idsBooks", "1, 2"))
                .andDo(print())
                .andExpect(status().isOk());

        verify(verifyLibraryBooksAvailableForLoanService, times(1)).verifyExistLibraryBooks(idsBooks);
    }
}
