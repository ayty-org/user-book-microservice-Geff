package br.com.phoebus.microservice.biblioteca.userandbook.librarybook.service;

import br.com.phoebus.microservice.biblioteca.userandbook.exceptions.LibraryBookAlreadyBorrowedException;
import br.com.phoebus.microservice.biblioteca.userandbook.exceptions.LibraryBookNotFoundException;
import br.com.phoebus.microservice.biblioteca.userandbook.librarybook.LibraryBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VerifyExistLibraryBooksServiceImpl implements VerifyExistLibraryBooksService {
    private final LibraryBookRepository libraryBookRepository;

    @Override
    public void verifyExistLibraryBooks(List<Long> idsBooks) {
        for (Long idBook : idsBooks) {
            if (!libraryBookRepository.existsById(idBook)) {
                throw new LibraryBookNotFoundException();
            }
            if(libraryBookRepository.getOne(idBook).isBorrowed()){
                throw new LibraryBookAlreadyBorrowedException();
            }
        }
    }
}
