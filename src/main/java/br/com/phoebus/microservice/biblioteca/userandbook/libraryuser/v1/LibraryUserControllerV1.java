package br.com.phoebus.microservice.biblioteca.userandbook.libraryuser.v1;

import br.com.phoebus.microservice.biblioteca.userandbook.libraryuser.LibraryUserDTO;
import br.com.phoebus.microservice.biblioteca.userandbook.libraryuser.service.GetLibraryUserService;
import br.com.phoebus.microservice.biblioteca.userandbook.libraryuser.service.SaveLibraryUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "v1/libraryUser")
public class LibraryUserControllerV1 {

    private final GetLibraryUserService getLibraryUserService;
    private final SaveLibraryUserService saveLibraryUserService;

    @GetMapping("/{id}")
    LibraryUserDTO getLibraryUser(@PathVariable(value = "id") Long id) {
        return getLibraryUserService.getLibraryUserForID(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    void saveLibraryUser(@Valid @RequestBody LibraryUserDTO libraryUserDTO) {
        saveLibraryUserService.saveLibraryUser(libraryUserDTO);
    }
}
