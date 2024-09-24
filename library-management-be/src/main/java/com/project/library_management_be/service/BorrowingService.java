package com.project.library_management_be.service;

import com.project.library_management_be.dto.BorrowingDTO;
import com.project.library_management_be.model.Borrowing;
import com.project.library_management_be.repository.BookRepository;
import com.project.library_management_be.repository.BorrowingRepository;
import com.project.library_management_be.repository.UserRepository;
import com.project.library_management_be.util.BorrowingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BorrowingService {

    @Autowired
    private BorrowingRepository borrowingRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BorrowingMapper borrowingMapper;

    public List<BorrowingDTO> getAllBorrowings() {
        return borrowingRepository.findAll().stream().map(borrowingMapper::borrowingToBorrowingDTO).collect(Collectors.toList());
    }

    public BorrowingDTO getBorrowingById(Long borrowingId) {
        Borrowing borrowing = borrowingRepository.findById(borrowingId).orElseThrow(() -> new RuntimeException("The borrowing is not found!"));
        return borrowingMapper.borrowingToBorrowingDTO(borrowing);
    }

    public BorrowingDTO addNewBorrowing(Borrowing borrowing) {
        Borrowing savedBorrowing = borrowingRepository.save(borrowing);
        return borrowingMapper.borrowingToBorrowingDTO(savedBorrowing);
    }

    public BorrowingDTO updateBorrowing(Borrowing updatedBorrowingData, Long borrowingId) {
        return borrowingRepository.findById(borrowingId).map(borrowing -> {
            borrowing.setUser(userRepository.findById(borrowing.getUser().getId()).orElseThrow(() -> new RuntimeException("User not found!")));
            borrowing.setBook(bookRepository.findById(borrowing.getBook().getId()).orElseThrow(() -> new RuntimeException("Book not found!")));
            borrowing.setBorrowStartDate(updatedBorrowingData.getBorrowStartDate());
            borrowing.setBorrowEndDate(updatedBorrowingData.getBorrowEndDate());
            borrowing.setComments(updatedBorrowingData.getComments());
            Borrowing updatedBorrowing = borrowingRepository.save(borrowing);
            return borrowingMapper.borrowingToBorrowingDTO(updatedBorrowing) ;
        }).orElseThrow(() -> new RuntimeException("The borrowing is not found!"));
    }

    public void deleteBorrowing(Long borrowingId) {
        Borrowing borrowingToDelete = borrowingRepository.findById(borrowingId).orElseThrow(() -> new RuntimeException("The borrowing is not found!"));
        borrowingRepository.delete(borrowingToDelete);
    }
}
