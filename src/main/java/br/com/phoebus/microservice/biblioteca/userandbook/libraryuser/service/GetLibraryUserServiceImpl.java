package br.com.phoebus.microservice.biblioteca.userandbook.libraryuser.service;

import br.com.phoebus.microservice.biblioteca.userandbook.exceptions.LibraryUserNotFoundException;
import br.com.phoebus.microservice.biblioteca.userandbook.libraryuser.LibraryUserDTO;
import br.com.phoebus.microservice.biblioteca.userandbook.libraryuser.LibraryUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetLibraryUserServiceImpl implements GetLibraryUserService {
    private final LibraryUserRepository libraryUserRepository;

    @Override
    public LibraryUserDTO getLibraryUserForID(Long id) {
        return LibraryUserDTO.from(libraryUserRepository.findById(id).orElseThrow(LibraryUserNotFoundException::new));
    }
}
