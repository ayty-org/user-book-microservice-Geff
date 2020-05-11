package br.com.phoebus.microservice.biblioteca.userandbook.libraryuser.service;


import br.com.phoebus.microservice.biblioteca.userandbook.libraryuser.LibraryUser;
import br.com.phoebus.microservice.biblioteca.userandbook.libraryuser.LibraryUserDTO;
import br.com.phoebus.microservice.biblioteca.userandbook.libraryuser.LibraryUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SaveLibraryUserServiceImpl implements SaveLibraryUserService {

    private final LibraryUserRepository libraryUserRepository;

    @Override
    public void saveLibraryUser(LibraryUserDTO libraryUserDTO) {
        libraryUserRepository.save(LibraryUser.to(libraryUserDTO));
    }
}
