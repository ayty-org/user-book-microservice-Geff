package br.com.phoebus.microservice.biblioteca.userandbook.librarybook.service;

import br.com.phoebus.microservice.biblioteca.userandbook.exceptions.LibraryBookNotFoundException;
import br.com.phoebus.microservice.biblioteca.userandbook.librarybook.LibraryBook;
import br.com.phoebus.microservice.biblioteca.userandbook.librarybook.LibraryBookDTO;
import br.com.phoebus.microservice.biblioteca.userandbook.librarybook.LibraryBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChangeIDLoanAndBorrowedBooksServiceImpl implements ChangeIDLoanAndBorrowedBooksService {
    private final LibraryBookRepository libraryBookRepository;
    private final GetAllBookForSpecificIDLoanService getAllBookForSpecificIDLoanService;

    @Override
    public void changeStatusAndBorrowed(Long idLoan, List<Long> idsBooks) {
        for (Long idBook : idsBooks) {
            if (!libraryBookRepository.existsById(idBook)) {
                throw new LibraryBookNotFoundException();
            }
        }
        LibraryBook libraryBook;
        List<Long> idsBooksOfLoan = new ArrayList<>();

        //Pego todos os livros que contem o ID do Loan e vejo se eles estão na lista de idsBooks
        //Caso exista um livro que não esteja na lista de idsBooks seguinifica que foi desvinculado do Loan
        //E então eu altero o status de Borrowed pra false e o ID do Loan para null
        List<LibraryBookDTO> libraryBookDTOList = getAllBookForSpecificIDLoanService.getAllBooksForSpecificId(idLoan);
        for (LibraryBookDTO libraryBookDTO : libraryBookDTOList) {
            if (!idsBooks.contains(libraryBookDTO.getId())) {
                libraryBook = libraryBookRepository.getOne(libraryBookDTO.getId());
                libraryBook.setBorrowed(false);
                libraryBook.setSpecificIDLoan(null);
                libraryBookRepository.save(libraryBook);
            } else {
                idsBooksOfLoan.add(libraryBookDTO.getId()); //faço isso aqui pra tirar o trabalho do for a baixo, para ele não precisar editar algo do msm jeito
            }
        }
        //Aqui eu pego os novos livros
        for (Long idBook : idsBooks) {
            if (!idsBooksOfLoan.contains(idBook)){
                libraryBook = libraryBookRepository.getOne(idBook);
                libraryBook.setBorrowed(true);
                libraryBook.setSpecificIDLoan(idLoan);
                libraryBookRepository.save(libraryBook);
            }
        }
    }
}
