package com.project.library_management_be.service;

import com.project.library_management_be.model.Borrowing;
import com.project.library_management_be.repository.BookRepository;
import com.project.library_management_be.repository.BorrowingRepository;
import com.project.library_management_be.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BorrowingService {

    @Autowired
    private BorrowingRepository borrowingRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;

    public List<Borrowing> getAllBorrowings() {
        return borrowingRepository.findAll();
    }

    public Borrowing getBorrowingById(Long borrowingId) {
        return borrowingRepository.findById(borrowingId).orElseThrow(() -> new RuntimeException("The borrowing is not found!"));
    }

    public Borrowing addNewBorrowing(Borrowing borrowing) {
        return borrowingRepository.save(borrowing);
    }

    public Borrowing updateBorrowing(Borrowing updatedBorrowing, Long borrowingId) {
        return borrowingRepository.findById(borrowingId).map(borrowing -> {
            borrowing.setUser(userRepository.findById(borrowing.getUser().getId()).orElseThrow(() -> new RuntimeException("User not found!")));
            borrowing.setBook(bookRepository.findById(borrowing.getBook().getId()).orElseThrow(() -> new RuntimeException("Book not found!")));
            borrowing.setBorrowStartDate(updatedBorrowing.getBorrowStartDate());
            borrowing.setBorrowEndDate(updatedBorrowing.getBorrowEndDate());
            borrowing.setComments(updatedBorrowing.getComments());
            return borrowingRepository.save(borrowing);
        }).orElseThrow(() -> new RuntimeException("The borrowing is not found!"));
    }

    public void deleteBorrowing(Long borrowingId) {
        Borrowing borrowingToDelete = borrowingRepository.findById(borrowingId).orElseThrow(() -> new RuntimeException("The borrowing is not found!"));
        borrowingRepository.delete(borrowingToDelete);
    }
}
