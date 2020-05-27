package br.com.phoebus.microservice.biblioteca.userandbook.librarybook.service;

import java.util.List;

@FunctionalInterface
public interface VerifyLibraryBooksAvailableForLoanService {

    void verifyExistLibraryBooks(List<Long> idsBooks);
}
