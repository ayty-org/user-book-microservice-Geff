package br.com.phoebus.microservice.biblioteca.userandbook.librarybook.service;

import java.util.List;

@FunctionalInterface
public interface ChangeIDLoanAndBorrowedBooksService {

    void changeStatusAndBorrowed(Long id, List<Long> idsBooks);
}
