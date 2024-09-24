package com.project.library_management_be.controller;

import com.project.library_management_be.dto.UserDTO;
import com.project.library_management_be.model.MembershipType;
import com.project.library_management_be.model.User;
import com.project.library_management_be.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/getAllUsers")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }
    @GetMapping("/getUserById")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<UserDTO> getUserById(@RequestParam(name = "id") Long userId) {
        UserDTO user = userService.getUserById(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    @PostMapping("/addUser")
    public ResponseEntity<String> addNewUser(@RequestBody User user) {
        userService.addNewUser(user);
        return new ResponseEntity<>("User successfully added to the database!", HttpStatus.CREATED);
    }

    @PutMapping("/updateUser")
    public ResponseEntity<UserDTO> updateUser(@RequestBody User user, @RequestParam(name = "id") Long userId) {
        UserDTO updatedUser = userService.updateUser(user, userId);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/deleteUser")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<Void> deleteUser(@RequestParam(name = "id") Long userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/addUserMembership")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<UserDTO> addUserMembership(@RequestParam(name = "id") Long userId,
                                                  @RequestParam(name = "membership_type") MembershipType membershipType) {
        UserDTO updatedUser = userService.addUserMembership(userId, membershipType);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @PatchMapping("/updateUserMembership")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<UserDTO> updateUserMembership(@RequestParam(name = "id") Long userId,
                                                     @RequestParam(name = "membership_type") MembershipType membershipType) {
        UserDTO updatedUser = userService.updateUserMemberShip(userId, membershipType);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }
    @DeleteMapping("/deleteUserMembership")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<Void> deleteUserMembership(@RequestParam(name = "id") Long userId) {
        userService.deleteUserMembership(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
