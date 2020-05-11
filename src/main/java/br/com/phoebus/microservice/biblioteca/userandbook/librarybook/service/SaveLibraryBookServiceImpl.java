package br.com.phoebus.microservice.biblioteca.userandbook.librarybook.service;

import br.com.phoebus.microservice.biblioteca.userandbook.librarybook.LibraryBook;
import br.com.phoebus.microservice.biblioteca.userandbook.librarybook.LibraryBookDTO;
import br.com.phoebus.microservice.biblioteca.userandbook.librarybook.LibraryBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SaveLibraryBookServiceImpl implements SaveLibraryBookService {

    private final LibraryBookRepository libraryBookRepository;


    @Override
    public void saveLibraryBook(LibraryBookDTO libraryBookDTO) {
        libraryBookRepository.save(LibraryBook.to(libraryBookDTO));
    }
}
