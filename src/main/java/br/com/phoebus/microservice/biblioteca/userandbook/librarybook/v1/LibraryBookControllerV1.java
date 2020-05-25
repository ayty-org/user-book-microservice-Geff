package br.com.phoebus.microservice.biblioteca.userandbook.librarybook.v1;

import br.com.phoebus.microservice.biblioteca.userandbook.librarybook.LibraryBookDTO;
import br.com.phoebus.microservice.biblioteca.userandbook.librarybook.service.DeleteLibraryBookService;
import br.com.phoebus.microservice.biblioteca.userandbook.librarybook.service.EditLibraryBookService;
import br.com.phoebus.microservice.biblioteca.userandbook.librarybook.service.VerifyExistLibraryBooksService;
import br.com.phoebus.microservice.biblioteca.userandbook.librarybook.service.ChangeIDLoanAndBorrowedBooksService;
import br.com.phoebus.microservice.biblioteca.userandbook.librarybook.service.GetAllBookForSpecificIDLoanService;
import br.com.phoebus.microservice.biblioteca.userandbook.librarybook.service.GetLibraryBookService;
import br.com.phoebus.microservice.biblioteca.userandbook.librarybook.service.ListLibraryBookService;
import br.com.phoebus.microservice.biblioteca.userandbook.librarybook.service.PageLibraryBookService;
import br.com.phoebus.microservice.biblioteca.userandbook.librarybook.service.SaveLibraryBookService;
import br.com.phoebus.microservice.biblioteca.userandbook.librarybook.service.VerifyJustExistBooksForIdService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "v1/libraryBook")
public class LibraryBookControllerV1 {

    private final DeleteLibraryBookService deleteLibraryBookService;
    private final EditLibraryBookService editLibraryBookService;
    private final VerifyExistLibraryBooksService verifyExistLibraryBooksService;
    private final ChangeIDLoanAndBorrowedBooksService changeIDLoanAndBorrowedBooksService;
    private final GetAllBookForSpecificIDLoanService getAllBookForSpecificIdLoanService;
    private final GetLibraryBookService getLibraryBookService;
    private final ListLibraryBookService listLibraryBookService;
    private final PageLibraryBookService pageLibraryBookService;
    private final SaveLibraryBookService saveLibraryBookService;
    private final VerifyJustExistBooksForIdService verifyJustExistBooksForIdService;

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteLibraryBookForId(@PathVariable(value = "id") Long id) {
        deleteLibraryBookService.deleteLibraryBook(id);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void editLibraryBook(@PathVariable(value = "id") Long id, @RequestBody LibraryBookDTO libraryBookDTO) {
        editLibraryBookService.editLibraryBook(id, libraryBookDTO);
    }

    @GetMapping(value = "/verifyJustExist", params = "idsBooks")
    void verifyJustExist(@RequestParam("idsBooks") List<Long> idsBooks) {
        verifyJustExistBooksForIdService.verifyJustExistBooksForId(idsBooks);
    }

    @GetMapping(value = "/verifyBooks", params = "idsBooks")
    void verifyBoooks(@RequestParam("idsBooks") List<Long> idsBooks) {
        verifyExistLibraryBooksService.verifyExistLibraryBooks(idsBooks);
    }
    //alterar URL para edit status e borrowed
    @PutMapping(value = "/changeStatus/{id}", params = "idsBooks")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void changeStatusBooks(@PathVariable("id") Long id, @RequestParam("idsBooks") List<Long> idsBooks) {
        changeIDLoanAndBorrowedBooksService.changeStatusAndBorrowed(id, idsBooks);
    }


    @GetMapping(value = "/getAllLoanBook/{id}")
    List<LibraryBookDTO> getAllLoanBook(@PathVariable(value = "id") Long id) {
        return getAllBookForSpecificIdLoanService.getAllBooksForSpecificId(id);
    }

    @GetMapping("/{id}")
    LibraryBookDTO getLibraryBook(@PathVariable(value = "id") Long id) {
        return getLibraryBookService.getLibraryBookForID(id);
    }

    @GetMapping
    List<LibraryBookDTO> listLibraryBook() {
        return listLibraryBookService.listLibraryBooks();
    }

    @GetMapping(params = {"page", "size"})
    Page<LibraryBookDTO> listOnPageLibraryBook(@RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        return pageLibraryBookService.listPageLibraryBooks(page, size);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    void saveLibraryBook(@Valid @RequestBody LibraryBookDTO libraryBookDTO) {
        saveLibraryBookService.saveLibraryBook(libraryBookDTO);
    }
}
