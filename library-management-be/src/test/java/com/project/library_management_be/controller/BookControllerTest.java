package com.project.library_management_be.controller;

import com.project.library_management_be.dto.AvailableBooksDTO;
import com.project.library_management_be.service.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookControllerTest {

    @InjectMocks
    private BookController bookController;
    @Mock
    private BookService bookService;

    @Test
    void testGetAllAvailableBooks() {
        // Arrange
        List<AvailableBooksDTO> availableBooks = Arrays.asList(
                new AvailableBooksDTO(),
                new AvailableBooksDTO(),
                new AvailableBooksDTO()
        );

        availableBooks.get(0).setTitle("Book 1");
        availableBooks.get(0).setQuantity(5);

        availableBooks.get(1).setTitle("Book 2");
        availableBooks.get(1).setQuantity(3);

        availableBooks.get(2).setTitle("Book 3");
        availableBooks.get(2).setQuantity(2);

        when(bookService.getAllAvailableBooks()).thenReturn(availableBooks);

        // Act
        ResponseEntity<List<AvailableBooksDTO>> response = bookController.getAllAvailableBooks();

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(availableBooks, response.getBody());
    }

}