package br.com.phoebus.microservice.biblioteca.userandbook.user.builders;

import br.com.phoebus.microservice.biblioteca.userandbook.libraryuser.LibraryUserDTO;

public class LibraryUserDTOBuilder {

    public static LibraryUserDTO.Builder createLibraryUserDTO() {
        return LibraryUserDTO.builder()
                .id(1L)
                .age(22)
                .name("Gefferson Pires")
                .telephone("83 9 93488354");
    }
}
