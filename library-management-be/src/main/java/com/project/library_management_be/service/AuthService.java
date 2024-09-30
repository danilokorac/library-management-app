package com.project.library_management_be.service;

import com.project.library_management_be.dto.LoginDTO;
import com.project.library_management_be.dto.RegisterDTO;
import com.project.library_management_be.exception.NotFoundException;
import com.project.library_management_be.model.Role;
import com.project.library_management_be.model.User;
import com.project.library_management_be.repository.UserRepository;
import com.project.library_management_be.util.UserMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager,
                       UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.userMapper = userMapper;
    }

    public User registerUser(RegisterDTO userToRegisterDTO) {
        if(userRepository.findByUsername(userToRegisterDTO.getUsername()).isPresent()) {
            throw new NotFoundException("User with the username: " + userToRegisterDTO.getUsername() + "already exists!");
        }

        User userToRegister = userMapper.registerDtoToUser(userToRegisterDTO);

        if(userToRegister.getMembershipType() == null) {
            throw new IllegalArgumentException("You need to choose membership type to proceed further!");
        }

        userToRegister.setUsername(userToRegisterDTO.getUsername());
        userToRegister.setPassword(passwordEncoder.encode(userToRegister.getPassword()));

        if(userToRegister.getRole() == null) {
            userToRegister.setRole(Role.MEMBER);
        }

        return userRepository.save(userToRegister);

    }

    public User login(LoginDTO loginDTO) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getUsername(),
                        loginDTO.getPassword()
                )
        );

        return userRepository.findByUsername(loginDTO.getUsername()).orElseThrow(
                () -> new NotFoundException("User not found in the database!"));
    }
}
