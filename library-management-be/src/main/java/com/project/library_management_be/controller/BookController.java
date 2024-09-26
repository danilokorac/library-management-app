package com.project.library_management_be.controller;

import com.project.library_management_be.dto.AvailableBooksDTO;
import com.project.library_management_be.dto.BookDTO;
import com.project.library_management_be.dto.UserDTO;
import com.project.library_management_be.model.Book;
import com.project.library_management_be.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping("/getAllBooks")
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        return new ResponseEntity<>(bookService.getAllBooks(), HttpStatus.OK);
    }
    @GetMapping("/getBookById")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<BookDTO> getBookById(@RequestParam(name = "id") Long bookId) {
        BookDTO book = bookService.getBookById(bookId);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }
    @PostMapping("/addBook")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<BookDTO> addNewBook(@RequestBody Book book) {
        BookDTO newBook =  bookService.addNewBook(book);
        return new ResponseEntity<>(newBook, HttpStatus.CREATED);
    }

    @PutMapping("/updateBook")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<BookDTO> updateBook(@RequestBody Book book, @RequestParam(name = "id") Long bookId) {
        BookDTO updatedBook = bookService.updateBook(book, bookId);
        return new ResponseEntity<>(updatedBook, HttpStatus.OK);
    }
    @DeleteMapping("/deleteBook")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<Void> deleteBook(@RequestParam(name = "id") Long bookId) {
        bookService.deleteBook(bookId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/getAllAvailableBooks")
    public ResponseEntity<List<AvailableBooksDTO>> getAllAvailableBooks() {
        return new ResponseEntity<>(bookService.getAllAvailableBooks(), HttpStatus.OK);
    }
}
