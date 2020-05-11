package br.com.phoebus.microservice.biblioteca.userandbook.librarybook.service;

import br.com.phoebus.microservice.biblioteca.userandbook.librarybook.LibraryBookDTO;

@FunctionalInterface
public interface SaveLibraryBookService {

    void saveLibraryBook(LibraryBookDTO libraryBookDTO);
}
