package br.com.phoebus.microservice.biblioteca.userandbook.librarybook.service;

import br.com.phoebus.microservice.biblioteca.userandbook.librarybook.LibraryBookDTO;

@FunctionalInterface
public interface GetLibraryBookService {

    LibraryBookDTO getLibraryBookForID(Long id);
}
