package br.com.phoebus.microservice.biblioteca.userandbook.libraryuser.v1;

import br.com.phoebus.microservice.biblioteca.userandbook.libraryuser.LibraryUserDTO;
import br.com.phoebus.microservice.biblioteca.userandbook.libraryuser.service.DeleteLibraryUserService;
import br.com.phoebus.microservice.biblioteca.userandbook.libraryuser.service.EditLibraryUserService;
import br.com.phoebus.microservice.biblioteca.userandbook.libraryuser.service.GetLibraryUserService;
import br.com.phoebus.microservice.biblioteca.userandbook.libraryuser.service.ListLibraryUserService;
import br.com.phoebus.microservice.biblioteca.userandbook.libraryuser.service.ListPageLibraryuserService;
import br.com.phoebus.microservice.biblioteca.userandbook.libraryuser.service.SaveLibraryUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "v1/libraryUser")
public class LibraryUserControllerV1 {

    private final DeleteLibraryUserService deleteLibraryUserService;
    private final EditLibraryUserService editLibraryUserService;
    private final GetLibraryUserService getLibraryUserService;
    private final ListLibraryUserService listLibraryUserService;
    private final ListPageLibraryuserService listPageLibraryuserService;
    private final SaveLibraryUserService saveLibraryUserService;

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteLibraryUserForId(@PathVariable(value = "id") Long id) {
        deleteLibraryUserService.deleteLibraryUser(id);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void editLibraryUser(@Valid @PathVariable(value = "id") Long id, @RequestBody LibraryUserDTO libraryUserDTO) {
        editLibraryUserService.editLibraryUser(id, libraryUserDTO);
    }

    @GetMapping("/{id}")
    LibraryUserDTO getLibraryUser(@PathVariable(value = "id") Long id) {
        return getLibraryUserService.getLibraryUserForID(id);
    }

    @GetMapping
    List<LibraryUserDTO> listLibraryUser() {
        return listLibraryUserService.listLibraryUsers();
    }

    @GetMapping(params = {"page", "size"})
    Page<LibraryUserDTO> listOnPageLibraryUser(@RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        return listPageLibraryuserService.listPageLibraryUser(page, size);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    void saveLibraryUser(@Valid @RequestBody LibraryUserDTO libraryUserDTO) {
        saveLibraryUserService.saveLibraryUser(libraryUserDTO);
    }
}
