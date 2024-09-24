package com.project.library_management_be.dto;

import com.project.library_management_be.model.Book;
import com.project.library_management_be.model.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BorrowingDTO {

    private UserDTO user;
    private BookDTO book;
    private LocalDate borrowStartDate;
    private LocalDate borrowEndDate;
    private String comments;
}
