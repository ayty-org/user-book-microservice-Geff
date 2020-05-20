package br.com.phoebus.microservice.biblioteca.userandbook.librarybook.service;

import br.com.phoebus.microservice.biblioteca.userandbook.exceptions.LibraryBookNotFoundException;
import br.com.phoebus.microservice.biblioteca.userandbook.librarybook.LibraryBook;
import br.com.phoebus.microservice.biblioteca.userandbook.librarybook.LibraryBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChangeStatusAndBorrowedBooksServiceImpl implements ChangeStatusAndBorrowedBooksService {
    private final LibraryBookRepository libraryBookRepository;

    @Override
    public void changeStatusAndBorrowed(Long id, List<Long> idsBooks) {
        for (Long idBook : idsBooks) {
            if (!libraryBookRepository.existsById(idBook)) {
                throw new LibraryBookNotFoundException();
            }
        }
        LibraryBook libraryBook;
        for (Long idBook : idsBooks) {
            libraryBook = libraryBookRepository.getOne(idBook);
            libraryBook.setBorrowed(true);
            libraryBook.setSpecificIDLoan(id);
            libraryBookRepository.save(libraryBook);
        }
    }
}
