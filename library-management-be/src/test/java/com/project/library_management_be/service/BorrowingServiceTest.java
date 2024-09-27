package com.project.library_management_be.service;

import com.project.library_management_be.dto.BorrowingDTO;
import com.project.library_management_be.model.Borrowing;
import com.project.library_management_be.repository.BorrowingRepository;
import com.project.library_management_be.util.BorrowingMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BorrowingServiceTest {

    @InjectMocks
    private BorrowingService borrowingService;

    @Mock
    private BorrowingRepository borrowingRepository;

    @Mock
    private BorrowingMapper borrowingMapper;

    @Test
    void testGetAllBorrowingsAndDebtByUserId() {
        // Arrange
        Long userId = 1L;
        Borrowing borrowing = new Borrowing();
        borrowing.setBorrowEndDate(LocalDate.of(2022, 1, 15));

        when(borrowingRepository.findByUserId(userId)).thenReturn(Arrays.asList(borrowing));

        BorrowingDTO expectedBorrowingDTO = new BorrowingDTO();
        expectedBorrowingDTO.setDebtAmount(100.0);

        when(borrowingMapper.borrowingToBorrowingDTO(borrowing)).thenReturn(expectedBorrowingDTO);

        // Act
        List<BorrowingDTO> result = borrowingService.getAllBorrowingsAndDebtByUserId(userId);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertNotNull(result.get(0));
        assertEquals(100.0, result.get(0).getDebtAmount());
    }
}