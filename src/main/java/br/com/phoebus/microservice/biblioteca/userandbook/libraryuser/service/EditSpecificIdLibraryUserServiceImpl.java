package br.com.phoebus.microservice.biblioteca.userandbook.libraryuser.service;

import br.com.phoebus.microservice.biblioteca.userandbook.exceptions.LibraryUserNotFoundException;
import br.com.phoebus.microservice.biblioteca.userandbook.libraryuser.LibraryUser;
import br.com.phoebus.microservice.biblioteca.userandbook.libraryuser.LibraryUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EditSpecificIdLibraryUserServiceImpl implements EditSpecificIdLibraryUserService{
    private final LibraryUserRepository libraryUserRepository;


    @Override
    public void editSpecifIdLibraryUser(Long id, String specificIDLoan) {
        System.out.println(specificIDLoan);
        LibraryUser libraryUser = libraryUserRepository.findById(id).orElseThrow(LibraryUserNotFoundException::new);
        if (specificIDLoan == "null"){
            libraryUser.setSpecificIDLoan(null);
        }else {
            libraryUser.setSpecificIDLoan(specificIDLoan);
        }

        libraryUserRepository.save(libraryUser);

    }
}
