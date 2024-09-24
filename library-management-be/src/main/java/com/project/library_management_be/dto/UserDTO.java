package com.project.library_management_be.dto;

import lombok.Data;

@Data
public class UserDTO {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String role;
    private String membershipType;
}
