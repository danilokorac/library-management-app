package com.project.library_management_be.util;

import com.project.library_management_be.dto.BorrowingDTO;
import com.project.library_management_be.model.Borrowing;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BorrowingMapper {
    BorrowingDTO borrowingToBorrowingDTO(Borrowing borrowing);
    Borrowing borrowingDtoToBorrowing(BorrowingDTO borrowingDTO);
}
