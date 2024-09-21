package com.project.library_management_be.controller;

import com.project.library_management_be.model.MembershipType;
import com.project.library_management_be.model.User;
import com.project.library_management_be.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/getUserById")
    public ResponseEntity<User> getUserById(@RequestParam(name = "id") Long userId) {
        User user = userService.getUserById(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    @PostMapping("/addUser")
    public ResponseEntity<User> addNewUser(@RequestBody User user) {
        User newUser = userService.addNewUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PutMapping("/updateUser")
    public ResponseEntity<User> updateUser(@RequestBody User user, @RequestParam(name = "id") Long userId) {
        User updatedUser = userService.updateUser(user, userId);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/deleteUser")
    public ResponseEntity<Void> deleteUser(@RequestParam(name = "id") Long userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/addUserMembership")
    public ResponseEntity<User> addUserMembership(@RequestParam(name = "id") Long userId,
                                                  @RequestParam(name = "membership_type") MembershipType membershipType) {
        User updatedUser = userService.addUserMembership(userId, membershipType);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @PatchMapping("/updateUserMembership")
    public ResponseEntity<User> updateUserMembership(@RequestParam(name = "id") Long userId,
                                                     @RequestParam(name = "membership_type") MembershipType membershipType) {
        User updatedUser = userService.updateUserMemberShip(userId, membershipType);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }
    @DeleteMapping("/deleteUserMembership")
    public ResponseEntity<User> deleteUserMembership(@RequestParam(name = "id") Long userId) {
        User updatedUser = userService.deleteUserMembership(userId);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }
}
