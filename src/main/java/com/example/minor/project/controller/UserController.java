package com.example.minor.project.controller;

import com.example.minor.project.Entity.User;
import com.example.minor.project.exception.UserAlreadyExistException;
import com.example.minor.project.exception.UserNotFoundException;
import com.example.minor.project.service.BookService;
import com.example.minor.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public User getBook(@PathVariable Long id) throws UserNotFoundException {
        return userService.findById(id);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) throws UserAlreadyExistException {
        // Additional logic to ensure you're updating the correct book
        return userService.save(user);
    }

    @PostMapping("/create")
    public User addUser(@RequestBody User user) throws UserAlreadyExistException {
        return userService.save(user);
    }

    @DeleteMapping("{id}")
    public void deleteUser(@PathVariable long id) throws UserNotFoundException {

        userService.deleteById(id);
    }
}
