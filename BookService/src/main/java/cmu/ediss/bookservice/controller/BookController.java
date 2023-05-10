package cmu.ediss.bookservice.controller;


import cmu.ediss.bookservice.entity.Book;
import cmu.ediss.bookservice.service.BookService;
import cmu.ediss.bookservice.untils.CommonResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.net.URI;


@Slf4j
@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private HttpServletRequest request;

//    public BookController(BookService bookService) {
//        this.bookService=bookService;
//        bookService.deleteAll();
//    }

    @PostMapping("")
    public ResponseEntity<?> createBook(@RequestBody @Valid Book book, BindingResult bindingResult) {
        log.info(book.toString());
        log.info(String.valueOf(bindingResult.hasErrors()));
        log.info(bindingResult.getAllErrors().toString());


        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (bookService.getByIsbn(book.getISBN()) != null) {
            return new ResponseEntity<>(new CommonResponse("This ISBN already exists in the system."), HttpStatus.UNPROCESSABLE_ENTITY);
        }

        boolean result = bookService.createBook(book);
        String baseUrl = String.format("%s://%s:%d", request.getScheme(), request.getServerName(), request.getServerPort());
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(baseUrl + "/books/" + book.getISBN()));


        return result ? new ResponseEntity<>(book, headers, HttpStatus.CREATED) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PutMapping("/{ISBN}")
    public ResponseEntity<?> updateBook(@RequestBody @Valid Book book, BindingResult bindingResult, @PathVariable String ISBN) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        boolean result = bookService.updateByISBN(book, ISBN);

        return result ? new ResponseEntity<>(book, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }


    @GetMapping({"/{ISBN}", "/isbn/{ISBN}"})
    public ResponseEntity<?> getBook(@PathVariable String ISBN) {
        Book result = bookService.getByIsbn(ISBN);
        return result == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{ISBN}/related-books")
    public ResponseEntity<?> retrieveRelatedBook(@PathVariable String ISBN) {
        var response = bookService.getRelatedBooks(ISBN);
        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }



}
