package com.project.library_management_be.util;

import com.project.library_management_be.dto.*;
import com.project.library_management_be.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User registerDtoToUser(RegisterDTO registerDTO);
    UserDTO userToUserDTO(User user);
    User userDtoToUser(UserDTO userDTO);





}
