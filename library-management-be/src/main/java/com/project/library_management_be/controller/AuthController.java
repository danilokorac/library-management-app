package com.project.library_management_be.controller;

import com.project.library_management_be.dto.LoginDTO;
import com.project.library_management_be.model.User;
import com.project.library_management_be.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {


    private  UserService userService;
    private  AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody @Validated User userToRegister) {
        try {
            userService.registerUser(userToRegister);
            return new ResponseEntity<>("User successfully created and added to the database!", HttpStatus.CREATED);
        } catch (IllegalArgumentException illegalArgumentException){
            return new ResponseEntity<>(illegalArgumentException.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> userLogin(@RequestBody LoginDTO loginCredentials) {
        try {
            // Authenticate the user
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginCredentials.getUsername(), loginCredentials.getPassword())
            );

            // If successful, set the authentication in the security context
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // You may generate and return a JWT token here if using JWT
            return new ResponseEntity<>("User successfully logged in", HttpStatus.OK);
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
        } catch (AuthenticationException e) {
            return new ResponseEntity<>("Authentication failed", HttpStatus.FORBIDDEN);
        }
    }
}
