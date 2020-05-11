package br.com.phoebus.microservice.biblioteca.userandbook.libraryuser.service;

import br.com.phoebus.microservice.biblioteca.userandbook.libraryuser.LibraryUserDTO;
import org.springframework.data.domain.Page;

@FunctionalInterface
public interface ListPageLibraryuserService {

    Page<LibraryUserDTO> listPageLibraryUser(Integer page, Integer size);
}
