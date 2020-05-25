package br.com.phoebus.microservice.biblioteca.userandbook.librarybook.service;

import br.com.phoebus.microservice.biblioteca.userandbook.librarybook.LibraryBookDTO;
import br.com.phoebus.microservice.biblioteca.userandbook.librarybook.LibraryBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class PageLibraryBookServiceImpl implements PageLibraryBookService {
    private final LibraryBookRepository libraryBookRepository;

    @Override
    public Page<LibraryBookDTO> listPageLibraryBooks(Integer page, Integer size) {
        return LibraryBookDTO.from(
                libraryBookRepository.findAll(
                        PageRequest.of(page, size, Sort.Direction.ASC, "id")));
    }
}
