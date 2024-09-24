package com.project.library_management_be.service;

import com.project.library_management_be.dto.AvailableBooksDTO;
import com.project.library_management_be.dto.BookDTO;
import com.project.library_management_be.model.Book;
import com.project.library_management_be.repository.BookRepository;
import com.project.library_management_be.util.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Autowired
    public BookService(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }


    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll().stream().map(bookMapper::bookToBookDTO).collect(Collectors.toList());
    }
    public BookDTO addNewBook(Book bookToAdd) {
        bookRepository.save(bookToAdd);
        return bookMapper.bookToBookDTO(bookToAdd);
    }

    public BookDTO updateBook(Book updatedBookData, Long bookId) {
        return bookRepository.findById(bookId).map(book -> {
            book.setTitle(updatedBookData.getTitle());
            book.setBookGenre(updatedBookData.getBookGenre());
            book.setStarRating(updatedBookData.getStarRating());
            book.setQuantity(updatedBookData.getQuantity());
            book.setStock(updatedBookData.getStock());
            Book updatedBook = bookRepository.save(book);
            return bookMapper.bookToBookDTO(updatedBook);
        }).orElseThrow(() -> new RuntimeException("Book with id: " + bookId + " is not found in the database!"));
    }

    public void deleteBook(Long bookId) {
         bookRepository.deleteById(bookId);
    }

    public List<AvailableBooksDTO> getAllAvailableBooks() {
        List<Book> listOfAvailableBooks = bookRepository.findByQuantityGreaterThan(0);
        return listOfAvailableBooks.stream().map(book -> {
            AvailableBooksDTO availableBooksDTO = new AvailableBooksDTO();
            availableBooksDTO.setTitle(book.getTitle());
            availableBooksDTO.setQuantity(book.getQuantity());
            return availableBooksDTO;
        }).collect(Collectors.toList());
    }
}
