package br.com.phoebus.microservice.biblioteca.userandbook.librarybook.service;

import br.com.phoebus.microservice.biblioteca.userandbook.exceptions.LibraryBookNotFoundException;
import br.com.phoebus.microservice.biblioteca.userandbook.librarybook.LibraryBook;
import br.com.phoebus.microservice.biblioteca.userandbook.librarybook.LibraryBookDTO;
import br.com.phoebus.microservice.biblioteca.userandbook.librarybook.LibraryBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EditLibraryBookServiceImpl implements EditLibraryBookService {

    private final LibraryBookRepository libraryBookRepository;

    @Override
    public void editLibraryBook(Long id, LibraryBookDTO libraryBookDTO) {
        LibraryBook libraryBook = libraryBookRepository.findById(id).orElseThrow(LibraryBookNotFoundException::new);

        libraryBook.setAuthor(libraryBookDTO.getAuthor());
        libraryBook.setIsbn(libraryBookDTO.getIsbn());
        libraryBook.setResume(libraryBookDTO.getResume());
        libraryBook.setTitle(libraryBookDTO.getTitle());
        libraryBook.setYear(libraryBookDTO.getYear());
        libraryBook.setBorrowed(libraryBookDTO.isBorrowed());
        libraryBook.setSpecificIDLoan(libraryBookDTO.getSpecificIDLoan());

        libraryBookRepository.save(libraryBook);
    }
}
