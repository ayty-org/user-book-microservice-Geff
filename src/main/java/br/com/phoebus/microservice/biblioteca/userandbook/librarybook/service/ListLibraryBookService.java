package br.com.phoebus.microservice.biblioteca.userandbook.librarybook.service;

import br.com.phoebus.microservice.biblioteca.userandbook.librarybook.LibraryBookDTO;

import java.util.List;

@FunctionalInterface
public interface ListLibraryBookService {

    List<LibraryBookDTO> listLibraryBooks();
}
