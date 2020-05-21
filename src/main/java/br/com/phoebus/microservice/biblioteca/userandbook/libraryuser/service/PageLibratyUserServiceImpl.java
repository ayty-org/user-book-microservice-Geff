package br.com.phoebus.microservice.biblioteca.userandbook.libraryuser.service;

import br.com.phoebus.microservice.biblioteca.userandbook.libraryuser.LibraryUserDTO;
import br.com.phoebus.microservice.biblioteca.userandbook.libraryuser.LibraryUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PageLibratyUserServiceImpl implements PageLibraryuserService {
    private final LibraryUserRepository libraryUserRepository;

    @Override
    public Page<LibraryUserDTO> listPageLibraryUser(Integer page, Integer size) {
        Pageable pageResquest = PageRequest.of(page, size, Sort.Direction.ASC, "id");
        return LibraryUserDTO.from(libraryUserRepository.findAll(pageResquest));
    }
}
