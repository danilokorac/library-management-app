package com.project.library_management_be.controller;

import com.project.library_management_be.model.Book;
import com.project.library_management_be.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookService bookService;

    @PostMapping("/addBook")
    public ResponseEntity<Book> addNewBook(@RequestBody Book book) {
        Book newBook =  bookService.addNewBook(book);
        return new ResponseEntity<>(newBook, HttpStatus.CREATED);
    }

    @PutMapping("/updateBook")
    public ResponseEntity<Book> updateBook(@RequestBody Book book, @RequestParam(name = "id") Long bookId) {
        Book updatedBook = bookService.updateBook(book, bookId);
        return new ResponseEntity<>(updatedBook, HttpStatus.OK);
    }
    @DeleteMapping("/deleteBook")
    public ResponseEntity<Void> deleteBook(@RequestParam(name = "id") Long bookId) {
        bookService.deleteBook(bookId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
