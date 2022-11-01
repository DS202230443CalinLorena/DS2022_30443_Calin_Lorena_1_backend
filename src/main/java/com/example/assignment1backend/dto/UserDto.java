package com.example.assignment1backend.dto;

import com.example.assignment1backend.model.Role;
import lombok.Getter;

@Getter
public class UserDto {
    private String name;
    private String email;
    private String password;
    private String role;
}
