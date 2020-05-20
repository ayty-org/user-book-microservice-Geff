package br.com.phoebus.microservice.biblioteca.userandbook.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class LibraryBookAlreadyBorrowedException extends RuntimeException {
    public LibraryBookAlreadyBorrowedException (){
        super("Book already borrowed");
    }
}
