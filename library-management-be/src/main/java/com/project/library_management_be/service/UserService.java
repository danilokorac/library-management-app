package com.project.library_management_be.service;

import com.project.library_management_be.model.MembershipType;
import com.project.library_management_be.model.Role;
import com.project.library_management_be.model.User;
import com.project.library_management_be.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Member;

@Service
public class UserService {

    private  UserRepository userRepository;
    private  PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(()-> new RuntimeException("User not found!"));
    }
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

    public User addUserMembership(Long userId, MembershipType membershipType) {
        return userRepository.findById(userId).map(user -> {
            if(user.getMembershipType() != null) {
                throw new RuntimeException("User already have existing membership!");
            }
            user.setMembershipType(membershipType);
            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("User with id: " + userId + " does not exist in the database!"));
    }

    public User updateUserMemberShip(Long userId, MembershipType membershipType) {
        return userRepository.findById(userId).map(user -> {
            user.setMembershipType(membershipType);
            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("User with id: " + userId + " does not exist in the database!"));
    }

    public User deleteUserMembership(Long userId) {
        return userRepository.findById(userId).map(user -> {
            user.setMembershipType(null);
            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("User with id: " + userId + " does not exist in the database!"));
    }

    public User registerUser(User userToRegister) {
        if(userRepository.findByUsername(userToRegister.getUsername()).isPresent()) {
            throw new RuntimeException("User with the username: " + userToRegister.getUsername() + "already exists!");
        }

        if(userToRegister.getMembershipType() == null) {
            throw new IllegalArgumentException("You need to choose membership type to proceed further!");
        }

        userToRegister.setPassword(passwordEncoder.encode(userToRegister.getPassword()));

        if(userToRegister.getRole() == null) {
            userToRegister.setRole(Role.MEMBER);
        }

        return userRepository.save(userToRegister);

    }
}
