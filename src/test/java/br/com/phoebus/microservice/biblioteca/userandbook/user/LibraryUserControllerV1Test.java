package br.com.phoebus.microservice.biblioteca.userandbook.user;

import br.com.phoebus.microservice.biblioteca.userandbook.exceptions.LibraryUserNotFoundException;
import br.com.phoebus.microservice.biblioteca.userandbook.libraryuser.LibraryUserDTO;
import br.com.phoebus.microservice.biblioteca.userandbook.libraryuser.service.DeleteLibraryUserService;
import br.com.phoebus.microservice.biblioteca.userandbook.libraryuser.service.EditLibraryUserService;
import br.com.phoebus.microservice.biblioteca.userandbook.libraryuser.service.EditSpecificIdLibraryUserService;
import br.com.phoebus.microservice.biblioteca.userandbook.libraryuser.service.GetLibraryUserService;
import br.com.phoebus.microservice.biblioteca.userandbook.libraryuser.service.ListLibraryUserService;
import br.com.phoebus.microservice.biblioteca.userandbook.libraryuser.service.PageLibraryuserService;
import br.com.phoebus.microservice.biblioteca.userandbook.libraryuser.service.SaveLibraryUserService;
import br.com.phoebus.microservice.biblioteca.userandbook.libraryuser.v1.LibraryUserControllerV1;
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

import static br.com.phoebus.microservice.biblioteca.userandbook.user.builders.LibraryUserDTOBuilder.createLibraryUserDTO;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.anyInt;
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
@WebMvcTest(LibraryUserControllerV1.class)
@DisplayName("Valida a funcionalidade do controlador de usuário")
public class LibraryUserControllerV1Test {

    private final String URL_USER = "/v1/libraryUser";
    private final String CONT_TYPE = "application/json";
    private final Long ID_USER = 1L;
    private final String UTF8 = "utf-8";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DeleteLibraryUserService deleteLibraryUserService;
    @MockBean
    private EditLibraryUserService editLibraryUserService;
    @MockBean
    private EditSpecificIdLibraryUserService editSpecificIdLibraryUserService;
    @MockBean
    private GetLibraryUserService getLibraryUserService;
    @MockBean
    private ListLibraryUserService listLibraryUserService;
    @MockBean
    private PageLibraryuserService pageLibraryuserService;
    @MockBean
    private SaveLibraryUserService saveLibraryUserService;

    @Test
    @DisplayName("Deleta um usuário")
    void shouldDeleteUserForID() throws Exception {

        mockMvc.perform(delete(URL_USER + "/{id}", ID_USER)
                .contentType(CONT_TYPE)
                .characterEncoding(UTF8))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(deleteLibraryUserService, times(1)).deleteLibraryUser(ID_USER);
    }

    @Test
    @DisplayName("Edita um usuário")
    void shouldEditUser() throws Exception {

        LibraryUserDTO libraryUserDTO = createLibraryUserDTO().age(30).build();

        mockMvc.perform(put(URL_USER + "/{id}", ID_USER)
                .contentType(CONT_TYPE)
                .characterEncoding(UTF8)
                .content(objectMapper.writeValueAsString(libraryUserDTO)))
                .andDo(print())
                .andExpect(status().isNoContent());

        ArgumentCaptor<LibraryUserDTO> captorLibraryUserDTO = ArgumentCaptor.forClass(LibraryUserDTO.class);
        ArgumentCaptor<Long> captorLong = ArgumentCaptor.forClass(Long.class);
        verify(editLibraryUserService, times(1)).editLibraryUser(captorLong.capture(), captorLibraryUserDTO.capture());
        LibraryUserDTO result = captorLibraryUserDTO.getValue();

        assertAll("User Controller",
                () -> assertThat(result.getId(), is(libraryUserDTO.getId())),
                () -> assertThat(result.getAge(), is(libraryUserDTO.getAge())),
                () -> assertThat(result.getTelephone(), is(libraryUserDTO.getTelephone())),
                () -> assertThat(result.getName(), is(libraryUserDTO.getName())),
                () -> assertThat(result.getSpecificIDLoan(), is(libraryUserDTO.getSpecificIDLoan())));
    }

    @Test
    @DisplayName("Retorna um status de BadRequest pela idade invalida ao editar")
    void shouldExceptionOnEditUserForAge() throws Exception {

        LibraryUserDTO libraryUserDTO = createLibraryUserDTO().age(1).build();

        mockMvc.perform(put(URL_USER + "/{id}", ID_USER)
                .contentType(CONT_TYPE)
                .characterEncoding(UTF8)
                .content(objectMapper.writeValueAsString(libraryUserDTO)))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(editLibraryUserService, times(0)).editLibraryUser(ID_USER, libraryUserDTO);
    }

    @Test
    @DisplayName("Retorna um status de BadRequest pelo nome ao editar")
    void shouldExceptionOnEditUserForName() throws Exception {

        LibraryUserDTO libraryUserDTO = createLibraryUserDTO().name("V").build();

        mockMvc.perform(put(URL_USER + "/{id}", ID_USER)
                .contentType(CONT_TYPE)
                .characterEncoding(UTF8)
                .content(objectMapper.writeValueAsString(libraryUserDTO)))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(editLibraryUserService, times(0)).editLibraryUser(ID_USER, libraryUserDTO);
    }

    @Test
    @DisplayName("Muda o SpecificIDLoan do usuário")
    void shouldEditSpecificIdLoan() throws Exception {

        mockMvc.perform(put(URL_USER + "/editUserSpecific/{id}", ID_USER)
                .contentType(CONT_TYPE)
                .characterEncoding(UTF8)
                .content(objectMapper.writeValueAsString("0001")))
                .andDo(print())
                .andExpect(status().isNoContent());

        ArgumentCaptor<String> captorString = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Long> captorLong = ArgumentCaptor.forClass(Long.class);

        verify(editSpecificIdLibraryUserService, times(1)).editSpecifIdLibraryUser(captorLong.capture(), captorString.capture());
        String result = captorString.getValue();
        Long resultId = captorLong.getValue();

        assertAll("Controller User",
                () -> assertThat(result, is("0001")),
                () -> assertThat(resultId, is(ID_USER))
        );
    }

    @Test
    @DisplayName("Pesquisa um usuário pelo ID")
    void shouldReturnLibraryUserDTO() throws Exception {

        LibraryUserDTO libraryUserDTO = createLibraryUserDTO().build();
        when(getLibraryUserService.getLibraryUserForID(ID_USER)).thenReturn(libraryUserDTO);

        MvcResult mvcResult = mockMvc.perform(get(URL_USER + "/{id}", ID_USER)
                .contentType(CONT_TYPE)
                .characterEncoding(UTF8))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String resultResponseBody = mvcResult.getResponse().getContentAsString();

        assertThat(objectMapper.writeValueAsString(libraryUserDTO), is(resultResponseBody));

        verify(getLibraryUserService, times(1)).getLibraryUserForID(ID_USER);
    }

    @Test
    @DisplayName("Lança uma exceção de usuário não encontrado")
    void shouldUserNotFoundOnGet() throws Exception {

        when(getLibraryUserService.getLibraryUserForID(ID_USER)).thenThrow(new LibraryUserNotFoundException());
        mockMvc.perform(get(URL_USER + "/{id}", ID_USER)
                .contentType(CONT_TYPE)
                .characterEncoding(UTF8))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Traz a lista de usuários")
    void shouldListUsers() throws Exception {

        LibraryUserDTO libraryUserDTO1 = createLibraryUserDTO().build();
        LibraryUserDTO libraryUserDTO2 = createLibraryUserDTO().id(2L).name("User 2").build();
        LibraryUserDTO libraryUserDTO3 = createLibraryUserDTO().id(3L).name("User 3").build();
        List<LibraryUserDTO> libraryUserDTOList = Arrays.asList(libraryUserDTO1, libraryUserDTO2, libraryUserDTO3);

        when(listLibraryUserService.listLibraryUsers()).thenReturn(libraryUserDTOList);

        MvcResult mvcResult = mockMvc.perform(get(URL_USER)
                .characterEncoding(UTF8)
                .contentType(CONT_TYPE))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String resultResponseBody = mvcResult.getResponse().getContentAsString();

        assertThat(objectMapper.writeValueAsString(libraryUserDTOList), is(resultResponseBody));
    }

    @Test
    @DisplayName("Traz a paginação dos usuários")
    void shouldPageUsers() throws Exception {

        LibraryUserDTO libraryUserDTO1 = createLibraryUserDTO().build();
        LibraryUserDTO libraryUserDTO2 = createLibraryUserDTO().id(2L).name("User 2").build();
        LibraryUserDTO libraryUserDTO3 = createLibraryUserDTO().id(3L).name("User 3").build();
        LibraryUserDTO libraryUserDTO4 = createLibraryUserDTO().id(4L).name("User 4").build();
        LibraryUserDTO libraryUserDTO5 = createLibraryUserDTO().id(5L).name("User 5").build();

        PageRequest pageRequest = PageRequest.of(1, 2);

        List<LibraryUserDTO> libraryUserDTOList = Arrays.asList(libraryUserDTO1,
                libraryUserDTO2,
                libraryUserDTO3,
                libraryUserDTO4,
                libraryUserDTO5);
        Page<LibraryUserDTO> libraryUserDTOPage = new PageImpl<>(libraryUserDTOList, pageRequest, libraryUserDTOList.size());

        when(pageLibraryuserService.listPageLibraryUser(anyInt(), anyInt())).thenReturn(libraryUserDTOPage);

        MvcResult mvcResult = mockMvc.perform(get(URL_USER)
                .contentType(CONT_TYPE)
                .contentType(UTF8)
                .param("page", "1").param("size", "2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String resultResponseBody = mvcResult.getResponse().getContentAsString();

        assertThat(objectMapper.writeValueAsString(libraryUserDTOPage), is(resultResponseBody));
    }

    @Test
    @DisplayName("Salva um usuário")
    void shouldSaveUser() throws Exception {

        LibraryUserDTO libraryUserDTO = createLibraryUserDTO().id(null).build();

        mockMvc.perform(post(URL_USER)
                .contentType(CONT_TYPE)
                .characterEncoding(UTF8)
                .content(objectMapper.writeValueAsString(libraryUserDTO)))
                .andDo(print())
                .andExpect(status().isCreated());

        ArgumentCaptor<LibraryUserDTO> captorLibraryUser = ArgumentCaptor.forClass(LibraryUserDTO.class);
        verify(saveLibraryUserService, times(1)).saveLibraryUser(captorLibraryUser.capture());
        LibraryUserDTO result = captorLibraryUser.getValue();

        assertAll("User Controller",
                () -> assertThat(result.getId(), is(libraryUserDTO.getId())),
                () -> assertThat(result.getSpecificIDLoan(), is(libraryUserDTO.getSpecificIDLoan())),
                () -> assertThat(result.getName(), is(libraryUserDTO.getName())),
                () -> assertThat(result.getTelephone(), is(libraryUserDTO.getTelephone())),
                () -> assertThat(result.getAge(), is(libraryUserDTO.getAge()))
        );
    }

    @Test
    @DisplayName("Lança uma exceção pelo nome")
    void shouldBadRequestForName() throws Exception {

        LibraryUserDTO libraryUserDTO = createLibraryUserDTO().name("F").id(null).build();

        mockMvc.perform(post(URL_USER)
                .contentType(CONT_TYPE)
                .characterEncoding(UTF8)
                .content(objectMapper.writeValueAsString(libraryUserDTO)))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(saveLibraryUserService, times(0)).saveLibraryUser(libraryUserDTO);
    }

    @Test
    @DisplayName("Lança uma exceção pela idade")
    void shouldBadRequestForAge() throws Exception {

        LibraryUserDTO libraryUserDTO = createLibraryUserDTO().age(2).id(null).build();

        mockMvc.perform(post(URL_USER)
                .contentType(CONT_TYPE)
                .characterEncoding(UTF8)
                .content(objectMapper.writeValueAsString(libraryUserDTO)))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(saveLibraryUserService, times(0)).saveLibraryUser(libraryUserDTO);
    }

    @Test
    @DisplayName("Lança uma exceção pelo telefone")
    void shouldBadRequestForTelephone() throws Exception {

        LibraryUserDTO libraryUserDTO = createLibraryUserDTO().telephone("999").id(null).build();

        mockMvc.perform(post(URL_USER)
                .contentType(CONT_TYPE)
                .characterEncoding(UTF8)
                .content(objectMapper.writeValueAsString(libraryUserDTO)))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(saveLibraryUserService, times(0)).saveLibraryUser(libraryUserDTO);
    }
}
