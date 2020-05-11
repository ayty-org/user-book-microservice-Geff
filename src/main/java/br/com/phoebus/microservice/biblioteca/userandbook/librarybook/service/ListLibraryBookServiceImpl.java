package br.com.phoebus.microservice.biblioteca.userandbook.librarybook.service;

import br.com.phoebus.microservice.biblioteca.userandbook.librarybook.LibraryBookDTO;
import br.com.phoebus.microservice.biblioteca.userandbook.librarybook.LibraryBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ListLibraryBookServiceImpl implements ListLibraryBookService {

    private final LibraryBookRepository libraryBookRepository;

    @Override
    public List<LibraryBookDTO> listLibraryBooks() {
        return LibraryBookDTO.from(libraryBookRepository.findAll());
    }
}
