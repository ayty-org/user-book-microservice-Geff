package br.com.phoebus.microservice.biblioteca.userandbook.librarybook.service;

import br.com.phoebus.microservice.biblioteca.userandbook.exceptions.LibraryBookNotFoundException;
import br.com.phoebus.microservice.biblioteca.userandbook.librarybook.LibraryBookDTO;
import br.com.phoebus.microservice.biblioteca.userandbook.librarybook.LibraryBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GetLibraryBookServiceImpl implements GetLibraryBookService {

    private final LibraryBookRepository libraryBookRepository;

    @Override
    public LibraryBookDTO getLibraryBookForID(Long id) {
        return LibraryBookDTO.from(libraryBookRepository.findById(id).orElseThrow(LibraryBookNotFoundException::new));
    }
}
