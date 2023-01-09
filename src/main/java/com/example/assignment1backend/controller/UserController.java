package com.example.assignment1backend.controller;

import com.example.assignment1backend.dto.LoginDto;
import com.example.assignment1backend.dto.UserDto;
import com.example.assignment1backend.exception.ResourceNotFoundEmailException;
import com.example.assignment1backend.exception.ResourceNotFoundException;
import com.example.assignment1backend.model.Device;
import com.example.assignment1backend.model.Role;
import com.example.assignment1backend.model.User;
import com.example.assignment1backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/userrole")
    public List<User> getAllUserRoleUsers() {
        List<User> allUsers = userRepository.findAll();
        List<User> userRoleUsers = new ArrayList<>();
        for (int i = 0; i < allUsers.size(); i++) {
            if(allUsers.get(i).getRole().equals(Role.USER)){
                userRoleUsers.add(allUsers.get(i));
            }
        }
        return userRoleUsers;
    }

    @PostMapping("/user")
    public User createUser(@RequestBody UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());
        user.setRole(Role.valueOf(userDto.getRole()));
        return userRepository.save(user);
    }

    @GetMapping("/user/{id}")
    public User getUserById(@PathVariable Long id) {
        User user =  userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
        return user;
    }

    @PutMapping("/user/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User newUser) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setName(newUser.getName());
                    user.setEmail(newUser.getEmail());
                    user.setPassword(newUser.getPassword());
                    return userRepository.save(user);
                }).orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @DeleteMapping("/user/{id}")
    public String deleteUser(@PathVariable Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));

        userRepository.delete(user);
        return "User with id = " + id + " was deleted.";
    }

    @GetMapping("/devicesByUserId/{id}")
    public List<Device> getDevicesByUserId(@PathVariable String id){
        return userRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new ResourceNotFoundException(Long.valueOf(id)))
                .getDevices();
    }

    @PostMapping("/login")
    public User login(@RequestBody LoginDto loginDto) {
        User user = userRepository.findByEmail(loginDto.getEmail())
                .orElseThrow(() -> new ResourceNotFoundEmailException(loginDto.getEmail()));
        if (!user.getPassword().equals(loginDto.getPassword())){
            throw new ResourceNotFoundEmailException(loginDto.getEmail());
        }
        return user;
    }

    @GetMapping("/user/email/{email}")
    public User getUserByEmail(@PathVariable String email) {
        User user =  userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundEmailException(email));
        return user;
    }
}
