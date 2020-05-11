package br.com.phoebus.microservice.biblioteca.userandbook.libraryuser.service;

import br.com.phoebus.microservice.biblioteca.userandbook.libraryuser.LibraryUserDTO;

import java.util.List;

@FunctionalInterface
public interface ListLibraryUserService {
    List<LibraryUserDTO> listLibraryUsers();
}
