package br.com.phoebus.microservice.biblioteca.userandbook.librarybook.service;

import br.com.phoebus.microservice.biblioteca.userandbook.librarybook.LibraryBookDTO;
import br.com.phoebus.microservice.biblioteca.userandbook.librarybook.LibraryBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAllBookForSpecificIDLoanServiceImpl implements GetAllBookForSpecificIDLoanService {
    private final LibraryBookRepository libraryBookRepository;

    @Override
    public List<LibraryBookDTO> getAllBooksForSpecificId(Long id) {
        return LibraryBookDTO.from(libraryBookRepository.findByspecificIDLoan(id));
    }
}
