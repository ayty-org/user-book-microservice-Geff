package br.com.phoebus.microservice.biblioteca.userandbook.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class LibraryUserNotFoundException extends RuntimeException{
    public LibraryUserNotFoundException () {
        super("Library User Not Found :c");
    }
}
