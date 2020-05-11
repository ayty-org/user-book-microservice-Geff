package br.com.phoebus.microservice.biblioteca.userandbook.librarybook.service;

import br.com.phoebus.microservice.biblioteca.userandbook.exceptions.LibraryBookNotFoundException;
import br.com.phoebus.microservice.biblioteca.userandbook.librarybook.LibraryBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DeleteLibraryBookServiceImpl implements DeleteLibraryBookService {

    private final LibraryBookRepository libraryBookRepository;


    @Override
    public void deleteLibraryBook(Long id) {
        if (!libraryBookRepository.existsById(id)) {
            throw new LibraryBookNotFoundException();
        }
        libraryBookRepository.deleteById(id);
    }
}
