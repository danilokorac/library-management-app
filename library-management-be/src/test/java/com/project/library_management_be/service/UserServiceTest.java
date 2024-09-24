package com.project.library_management_be.service;

import com.project.library_management_be.dto.RegisterDTO;
import com.project.library_management_be.exception.NotFoundException;
import com.project.library_management_be.model.MembershipType;
import com.project.library_management_be.model.Role;
import com.project.library_management_be.model.User;
import com.project.library_management_be.repository.UserRepository;
import com.project.library_management_be.util.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Test
    void testRegisterUser_usernameAlreadyExists() {
        // Arrange
        RegisterDTO userToRegisterDTO = new RegisterDTO();
        userToRegisterDTO.setUsername("username");
        userToRegisterDTO.setPassword("password");
        userToRegisterDTO.setMembershipType(MembershipType.FREE.name());

        when(userRepository.findByUsername(userToRegisterDTO.getUsername())).thenReturn(Optional.of(new User()));

        // Act and Assert
        assertThrows(NotFoundException.class, () -> userService.registerUser(userToRegisterDTO));
    }

    @Test
    void testRegisterUser_noMembershipType() {
        // Arrange
        RegisterDTO userToRegisterDTO = new RegisterDTO();
        userToRegisterDTO.setUsername("username");
        userToRegisterDTO.setPassword("password");

        when(userRepository.findByUsername(userToRegisterDTO.getUsername())).thenReturn(Optional.empty());
        when(userMapper.registerDtoToUser(userToRegisterDTO)).thenReturn(new User());

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> userService.registerUser(userToRegisterDTO));
    }
}