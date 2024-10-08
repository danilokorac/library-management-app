package com.project.library_management_be.service;

import com.project.library_management_be.dto.BorrowingDTO;
import com.project.library_management_be.exception.NotFoundException;
import com.project.library_management_be.model.Borrowing;
import com.project.library_management_be.repository.BookRepository;
import com.project.library_management_be.repository.BorrowingRepository;
import com.project.library_management_be.repository.UserRepository;
import com.project.library_management_be.util.BorrowingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BorrowingService {


    private final BorrowingRepository borrowingRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final BorrowingMapper borrowingMapper;

    @Autowired
    public BorrowingService(BorrowingRepository borrowingRepository, UserRepository userRepository, BookRepository bookRepository, BorrowingMapper borrowingMapper) {
        this.borrowingRepository = borrowingRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.borrowingMapper = borrowingMapper;
    }

    public List<BorrowingDTO> getAllBorrowings() {
        return borrowingRepository.findAll().stream().map(borrowingMapper::borrowingToBorrowingDTO).collect(Collectors.toList());
    }

    public BorrowingDTO getBorrowingById(Long borrowingId) {
        Borrowing borrowing = borrowingRepository.findById(borrowingId).orElseThrow(() -> new NotFoundException("The borrowing is not found!"));
        return borrowingMapper.borrowingToBorrowingDTO(borrowing);
    }

    public BorrowingDTO addNewBorrowing(Borrowing borrowing) {
        Borrowing savedBorrowing = borrowingRepository.save(borrowing);
        return borrowingMapper.borrowingToBorrowingDTO(savedBorrowing);
    }

    public BorrowingDTO updateBorrowing(Borrowing updatedBorrowingData, Long borrowingId) {
        return borrowingRepository.findById(borrowingId).map(borrowing -> {
            borrowing.setUser(userRepository.findById(borrowing.getUser().getId()).orElseThrow(() -> new NotFoundException("User not found!")));
            borrowing.setBook(bookRepository.findById(borrowing.getBook().getId()).orElseThrow(() -> new NotFoundException("Book not found!")));
            borrowing.setBorrowStartDate(updatedBorrowingData.getBorrowStartDate());
            borrowing.setBorrowEndDate(updatedBorrowingData.getBorrowEndDate());
            borrowing.setComments(updatedBorrowingData.getComments());
            borrowing.setDebtAmount(updatedBorrowingData.getDebtAmount());
            Borrowing updatedBorrowing = borrowingRepository.save(borrowing);
            return borrowingMapper.borrowingToBorrowingDTO(updatedBorrowing) ;
        }).orElseThrow(() -> new NotFoundException("The borrowing is not found!"));
    }

    public void deleteBorrowing(Long borrowingId) {
        Borrowing borrowingToDelete = borrowingRepository.findById(borrowingId).orElseThrow(() -> new NotFoundException("The borrowing is not found!"));
        borrowingRepository.delete(borrowingToDelete);
    }

    public List<BorrowingDTO> getAllBorrowingsAndDebtByUserUsername(String username) {
        return borrowingRepository.findByUserUsername(username)
                .stream()
                .map(borrowing -> {
                    if(borrowing.getBorrowEndDate().isBefore(LocalDate.now())) {
                        borrowing.setDebtAmount(100.0);
                    } else {
                        borrowing.setDebtAmount(0.0);
                    }

                    return borrowingMapper.borrowingToBorrowingDTO(borrowing);
                })
                .collect(Collectors.toList());
    }
}
