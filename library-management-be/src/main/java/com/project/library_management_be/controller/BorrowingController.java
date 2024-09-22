package com.project.library_management_be.controller;

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
    public List<Borrowing> getAllBorrowings() {
        return borrowingService.getAllBorrowings();
    }

    @GetMapping("/getBorrowingById")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<Borrowing> getBorrowingById(@RequestParam(name = "id") Long borrowingId) {
        Borrowing borrowing = borrowingService.getBorrowingById(borrowingId);
        return new ResponseEntity<>(borrowing, HttpStatus.OK);
    }

    @PostMapping("/addNewBorrowing")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<Borrowing> addNewBorrowing(@RequestBody Borrowing borrowing) {
        Borrowing newBorrowing = borrowingService.addNewBorrowing(borrowing);
        return new ResponseEntity<>(newBorrowing, HttpStatus.CREATED);
    }

    @PutMapping("/updateBorrowing")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<Borrowing> updateBorrowing(@RequestBody Borrowing borrowing, @RequestParam(name = "id") Long borrowingId) {
        Borrowing updatedBorrowing = borrowingService.updateBorrowing(borrowing, borrowingId);
        return new ResponseEntity<>(updatedBorrowing, HttpStatus.OK);
    }

    @DeleteMapping("/deleteBorrowing")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<Void> deleteBorrowing(@RequestParam(name = "id") Long borrowingId) {
        borrowingService.deleteBorrowing(borrowingId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
