package br.com.phoebus.microservice.biblioteca.userandbook.librarybook.service;

import br.com.phoebus.microservice.biblioteca.userandbook.librarybook.LibraryBookDTO;
import org.springframework.data.domain.Page;

@FunctionalInterface
public interface PageLibraryBookService {

    Page<LibraryBookDTO> listPageLibraryBooks(Integer page, Integer size);
}
