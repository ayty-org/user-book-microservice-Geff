package br.com.phoebus.microservice.biblioteca.userandbook.librarybook;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibraryBookRepository extends JpaRepository<LibraryBook, Long> {

    List<LibraryBook> findByspecificIDLoan(Long idLoan);
}
