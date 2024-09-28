package com.project.library_management_be.controller;

import com.project.library_management_be.config.JwtTokenProvider;
import com.project.library_management_be.dto.AuthResponseDTO;
import com.project.library_management_be.dto.LoginDTO;
import com.project.library_management_be.dto.RegisterDTO;
import com.project.library_management_be.model.User;
import com.project.library_management_be.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthService authService;

    public AuthController(JwtTokenProvider jwtTokenProvider, AuthService authService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody @Validated RegisterDTO userToRegisterDTO) {
        try {
            User registeredUser = authService.registerUser(userToRegisterDTO);
            return new ResponseEntity<>(registeredUser,  HttpStatus.CREATED);
        } catch (IllegalArgumentException illegalArgumentException){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> userLogin(@RequestBody @Validated LoginDTO loginCredentials) {

        User authenticatedUser = authService.login(loginCredentials);

        String jwtToken = jwtTokenProvider.generateToken(authenticatedUser);

        AuthResponseDTO authResponse = new AuthResponseDTO();
        authResponse.setAccessToken(jwtToken);
        authResponse.setExpiresIn(jwtTokenProvider.getJwtExpirationDate());

        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }
}
