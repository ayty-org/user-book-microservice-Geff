package br.com.phoebus.microservice.biblioteca.userandbook.user.builders;

import br.com.phoebus.microservice.biblioteca.userandbook.libraryuser.LibraryUser;

public class LibraryUserBuilder {

    public static LibraryUser.Builder createLibraryUser() {
        return LibraryUser.builder()
                .id(1L)
                .age(22)
                .name("Gefferson Pires")
                .telephone("83 9 93488354");
    }
}
