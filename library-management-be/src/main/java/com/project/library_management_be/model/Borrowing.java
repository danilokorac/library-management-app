package com.project.library_management_be.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Data
@Table(name = "borrowings")
public class Borrowing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    private LocalDate borrowStartDate;
    private LocalDate borrowEndDate;
    private String comments;
    private Double debtAmount; // Amount if the book is not brought back after borrowEndDate

}
