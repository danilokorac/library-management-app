package com.project.library_management_be.service;

import com.project.library_management_be.model.Book;
import com.project.library_management_be.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public Book addNewBook(Book book) {
        return bookRepository.save(book);
    }

    public Book updateBook(Book updatedBook, Long bookId) {
        return bookRepository.findById(bookId).map(book -> {
            book.setTitle(updatedBook.getTitle());
            book.setBookGenre(updatedBook.getBookGenre());
            book.setStarRating(updatedBook.getStarRating());
            book.setQuantity(updatedBook.getQuantity());
            book.setStock(updatedBook.getStock());
            return bookRepository.save(book);
        }).orElseThrow(() -> new RuntimeException("Book with id: " + bookId + " is not found in the database!"));
    }

    public void deleteBook(Long bookId) {
         bookRepository.deleteById(bookId);
    }
}
