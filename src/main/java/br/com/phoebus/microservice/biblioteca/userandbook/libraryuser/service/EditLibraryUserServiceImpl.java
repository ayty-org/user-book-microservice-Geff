package br.com.phoebus.microservice.biblioteca.userandbook.libraryuser.service;

import br.com.phoebus.microservice.biblioteca.userandbook.exceptions.LibraryUserNotFoundException;
import br.com.phoebus.microservice.biblioteca.userandbook.libraryuser.LibraryUser;
import br.com.phoebus.microservice.biblioteca.userandbook.libraryuser.LibraryUserDTO;
import br.com.phoebus.microservice.biblioteca.userandbook.libraryuser.LibraryUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EditLibraryUserServiceImpl implements EditLibraryUserService {

    private final LibraryUserRepository libraryUserRepository;

    @Override
    public void editLibraryUser(Long id, LibraryUserDTO libraryUserDTO) {
        LibraryUser libraryUser = libraryUserRepository.findById(id).orElseThrow(LibraryUserNotFoundException::new);

        libraryUser.setAge(libraryUserDTO.getAge());
        libraryUser.setName(libraryUserDTO.getName());
        libraryUser.setTelephone(libraryUserDTO.getTelephone());

        libraryUserRepository.save(libraryUser);
    }
}
