package com.project.library_management_be.util;

import com.project.library_management_be.dto.AvailableBooksDTO;
import com.project.library_management_be.dto.BookDTO;
import com.project.library_management_be.model.Book;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper {

    BookDTO bookToBookDTO(Book book);
}
