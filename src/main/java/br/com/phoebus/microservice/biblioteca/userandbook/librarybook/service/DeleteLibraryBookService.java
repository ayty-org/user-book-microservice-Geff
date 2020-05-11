package br.com.phoebus.microservice.biblioteca.userandbook.librarybook.service;

@FunctionalInterface
public interface DeleteLibraryBookService {

    void deleteLibraryBook(Long id);
}
