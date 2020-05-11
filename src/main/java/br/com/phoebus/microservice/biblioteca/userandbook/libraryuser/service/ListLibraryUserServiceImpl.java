package br.com.phoebus.microservice.biblioteca.userandbook.libraryuser.service;

import br.com.phoebus.microservice.biblioteca.userandbook.libraryuser.LibraryUserDTO;
import br.com.phoebus.microservice.biblioteca.userandbook.libraryuser.LibraryUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ListLibraryUserServiceImpl implements ListLibraryUserService {

    private final LibraryUserRepository libraryUserRepository;

    @Override
    public List<LibraryUserDTO> listLibraryUsers() {
        return LibraryUserDTO.from(libraryUserRepository.findAll());
    }
}
