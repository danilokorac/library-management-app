package com.project.library_management_be.controller;

import com.project.library_management_be.dto.BorrowingDTO;
import com.project.library_management_be.model.Borrowing;
import com.project.library_management_be.service.BorrowingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/borrowing")
public class BorrowingController {

    @Autowired
    private BorrowingService borrowingService;

    @GetMapping("/getAllBorrowings")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public List<BorrowingDTO> getAllBorrowings() {
        return borrowingService.getAllBorrowings();
    }

    @GetMapping("/getBorrowingById")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<BorrowingDTO> getBorrowingById(@RequestParam(name = "id") Long borrowingId) {
        BorrowingDTO borrowing = borrowingService.getBorrowingById(borrowingId);
        return new ResponseEntity<>(borrowing, HttpStatus.OK);
    }

    @PostMapping("/addNewBorrowing")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<String> addNewBorrowing(@RequestBody Borrowing borrowing) {
        borrowingService.addNewBorrowing(borrowing);
        return new ResponseEntity<>("Borrowing successfully added to the database!", HttpStatus.CREATED);
    }

    @PutMapping("/updateBorrowing")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<BorrowingDTO> updateBorrowing(@RequestBody Borrowing borrowing, @RequestParam(name = "id") Long borrowingId) {
        BorrowingDTO updatedBorrowing = borrowingService.updateBorrowing(borrowing, borrowingId);
        return new ResponseEntity<>(updatedBorrowing, HttpStatus.OK);
    }

    @DeleteMapping("/deleteBorrowing")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<Void> deleteBorrowing(@RequestParam(name = "id") Long borrowingId) {
        borrowingService.deleteBorrowing(borrowingId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/getAllBorrowingsByUserId")
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('MEMBER')")
    public ResponseEntity<List<BorrowingDTO>> getAllBorrowingsByUserId(@RequestParam(name = "id") Long userId) {
        List<BorrowingDTO> userBorrowings = borrowingService.getAllBorrowingsByUserId(userId);
        return new ResponseEntity<>(userBorrowings, HttpStatus.OK);
    }

}
