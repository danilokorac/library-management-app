package com.project.library_management_be.repository;

import com.project.library_management_be.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByQuantityGreaterThan(int quantity);
}
