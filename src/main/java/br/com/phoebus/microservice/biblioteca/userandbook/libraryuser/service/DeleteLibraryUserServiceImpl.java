package br.com.phoebus.microservice.biblioteca.userandbook.libraryuser.service;

import br.com.phoebus.microservice.biblioteca.userandbook.exceptions.LibraryUserNotFoundException;
import br.com.phoebus.microservice.biblioteca.userandbook.libraryuser.LibraryUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DeleteLibraryUserServiceImpl implements DeleteLibraryUserService {

    private final LibraryUserRepository libraryUserRepository;

    @Override
    public void deleteLibraryUser(Long id) {
        if (!libraryUserRepository.existsById(id)) {
            throw new LibraryUserNotFoundException();
        }
        libraryUserRepository.deleteById(id);
    }
}
