package br.com.phoebus.microservice.biblioteca.userandbook.librarybook.service;

import br.com.phoebus.microservice.biblioteca.userandbook.exceptions.LibraryBookNotFoundException;
import br.com.phoebus.microservice.biblioteca.userandbook.librarybook.LibraryBook;
import br.com.phoebus.microservice.biblioteca.userandbook.librarybook.LibraryBookDTO;
import br.com.phoebus.microservice.biblioteca.userandbook.librarybook.LibraryBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChangeStatusAndBorrowedBooksServiceImpl implements ChangeStatusAndBorrowedBooksService {
    private final LibraryBookRepository libraryBookRepository;
    private final GetAllBookForSpecificIDLoanService getAllBookForSpecificIDLoanService;

    @Override
    public void changeStatusAndBorrowed(Long id, List<Long> idsBooks) {
        for (Long idBook : idsBooks) {
            if (!libraryBookRepository.existsById(idBook)) {
                throw new LibraryBookNotFoundException();
            }
        }

        LibraryBook libraryBook;
        List<LibraryBookDTO> libraryBookDTOList = getAllBookForSpecificIDLoanService.getAllBooksForSpecificId(id);

        for (LibraryBookDTO libraryBookDTO : libraryBookDTOList) {
            if (!idsBooks.contains(libraryBookDTO.getSpecificIDLoan())) {
                libraryBook = libraryBookRepository.getOne(libraryBookDTO.getId());
                libraryBook.setBorrowed(false);
                libraryBook.setSpecificIDLoan(null);
                libraryBookRepository.save(libraryBook);
            } else {
                idsBooks.remove(libraryBookDTO.getId()); //faço isso aqui pra tirar o trabalho do for a baixo, para ele não precisar editar algo do msm jeito
            }
        }

        for (Long idBook : idsBooks) {
            libraryBook = libraryBookRepository.getOne(idBook);
            libraryBook.setBorrowed(true);
            libraryBook.setSpecificIDLoan(id);
            libraryBookRepository.save(libraryBook);
        }
    }
}
