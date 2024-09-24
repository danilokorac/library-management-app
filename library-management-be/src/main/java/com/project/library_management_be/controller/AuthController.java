package com.project.library_management_be.controller;

import com.project.library_management_be.dto.AuthResponseDTO;
import com.project.library_management_be.dto.LoginDTO;
import com.project.library_management_be.dto.RegisterDTO;
import com.project.library_management_be.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody @Validated RegisterDTO userToRegisterDTO) {
        try {
            userService.registerUser(userToRegisterDTO);
            return new ResponseEntity<>("User successfully created and added to the database!", HttpStatus.CREATED);
        } catch (IllegalArgumentException illegalArgumentException){
            return new ResponseEntity<>(illegalArgumentException.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> userLogin(@RequestBody @Validated LoginDTO loginCredentials) {

        String token = userService.login(loginCredentials);

        AuthResponseDTO authResponseDTO = new AuthResponseDTO();
        authResponseDTO.setAccessToken(token);

        return new ResponseEntity<>(authResponseDTO, HttpStatus.OK);
    }
}
