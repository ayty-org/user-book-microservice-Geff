package br.com.phoebus.microservice.biblioteca.userandbook.libraryuser.v1;

import br.com.phoebus.microservice.biblioteca.userandbook.libraryuser.LibraryUserDTO;
import br.com.phoebus.microservice.biblioteca.userandbook.libraryuser.service.GetLibraryUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "v1/libraryUser")
public class LibraryUserControllerV1 {

    private final GetLibraryUserService getLibraryUserService;

    @GetMapping("/{id}")
    LibraryUserDTO getLibraryUser(@PathVariable(value = "id") Long id) {
        return getLibraryUserService.getLibraryUserForID(id);
    }
}
