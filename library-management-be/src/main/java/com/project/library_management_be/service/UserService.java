package com.project.library_management_be.service;

import com.project.library_management_be.model.User;
import com.project.library_management_be.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User addNewUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(User updatedUser, Long userId) {
        return userRepository.findById(userId).map(user -> {
            user.setUsername(updatedUser.getUsername());
            user.setPassword(updatedUser.getPassword());
            user.setFirstName(updatedUser.getFirstName());
            user.setLastName(updatedUser.getLastName());
            user.setEmail(updatedUser.getEmail());
            user.setRole(updatedUser.getRole());
            user.setMembershipType(updatedUser.getMembershipType());
            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("User with id: " + userId + " does not exist in the database!"));
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}
