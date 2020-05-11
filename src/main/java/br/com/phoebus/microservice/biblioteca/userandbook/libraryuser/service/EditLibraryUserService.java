package br.com.phoebus.microservice.biblioteca.userandbook.libraryuser.service;

import br.com.phoebus.microservice.biblioteca.userandbook.libraryuser.LibraryUserDTO;

@FunctionalInterface
public interface EditLibraryUserService {

    void editLibraryUser(Long id, LibraryUserDTO libraryUserDTO);
}
