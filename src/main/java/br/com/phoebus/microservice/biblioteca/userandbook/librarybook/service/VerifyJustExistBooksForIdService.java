package br.com.phoebus.microservice.biblioteca.userandbook.librarybook.service;

import java.util.List;

@FunctionalInterface
public interface VerifyJustExistBooksForIdService {

    void verifyJustExistBooksForId(List<Long> idsBooks);
}
