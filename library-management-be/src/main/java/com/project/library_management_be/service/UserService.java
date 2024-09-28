package com.project.library_management_be.service;

import com.project.library_management_be.config.JwtTokenProvider;
import com.project.library_management_be.dto.LoginDTO;
import com.project.library_management_be.dto.RegisterDTO;
import com.project.library_management_be.dto.UserDTO;
import com.project.library_management_be.exception.NotFoundException;
import com.project.library_management_be.model.MembershipType;
import com.project.library_management_be.model.Role;
import com.project.library_management_be.model.User;
import com.project.library_management_be.repository.UserRepository;
import com.project.library_management_be.util.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final  UserRepository userRepository;
    private final  PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Autowired
    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       UserMapper userMapper) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream().map(userMapper::userToUserDTO).collect(Collectors.toList());
    }

    public UserDTO getUserById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(()-> new NotFoundException("User not found!"));
        return userMapper.userToUserDTO(user);
    }
    public UserDTO addNewUser(User userToAdd) {
        userToAdd.setPassword(passwordEncoder.encode(userToAdd.getPassword()));
        userRepository.save(userToAdd);
        return userMapper.userToUserDTO(userToAdd);
    }

    public UserDTO updateUser(User updatedUserData, Long userId) {
        return userRepository.findById(userId).map(user -> {
            user.setUsername(updatedUserData.getUsername());
            user.setPassword(passwordEncoder.encode(updatedUserData.getPassword()));
            user.setFirstName(updatedUserData.getFirstName());
            user.setLastName(updatedUserData.getLastName());
            user.setEmail(updatedUserData.getEmail());
            user.setMembershipType(updatedUserData.getMembershipType());
            User updateUser = userRepository.save(user);
            return userMapper.userToUserDTO(updateUser);
        }).orElseThrow(() -> new NotFoundException("User with id: " + userId + " does not exist in the database!"));
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    public UserDTO addUserMembership(Long userId, MembershipType membershipType) {
        return userRepository.findById(userId).map(user -> {
            if(user.getMembershipType() != null) {
                throw new NotFoundException("User already have existing membership!");
            }
            user.setMembershipType(membershipType);
            User updatedUser = userRepository.save(user);
            return userMapper.userToUserDTO(updatedUser);
        }).orElseThrow(() -> new NotFoundException("User with id: " + userId + " does not exist in the database!"));
    }

    public UserDTO updateUserMemberShip(Long userId, MembershipType membershipType) {
        return userRepository.findById(userId).map(user -> {
            user.setMembershipType(membershipType);
            User updatedUser = userRepository.save(user);
            return userMapper.userToUserDTO(updatedUser);
        }).orElseThrow(() -> new NotFoundException("User with id: " + userId + " does not exist in the database!"));
    }

    public void deleteUserMembership(Long userId) {
        userRepository.findById(userId).map(user -> {
            user.setMembershipType(null);
            return userRepository.save(user);
        }).orElseThrow(() -> new NotFoundException("User with id: " + userId + " does not exist in the database!"));
    }

    public UserDTO authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        return userMapper.userToUserDTO(currentUser);
    }

}
