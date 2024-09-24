package com.project.library_management_be.util;

import com.project.library_management_be.dto.*;
import com.project.library_management_be.model.Book;
import com.project.library_management_be.model.Borrowing;
import com.project.library_management_be.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    // Mapping between RegisterDTO and User entity
    User registerDtoToUser(RegisterDTO registerDTO);

    // Mapping User entity to UserDTO
    UserDTO userToUserDTO(User user);

    User userDtoToUser(UserDTO userDTO);





}
