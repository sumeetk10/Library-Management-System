package com.example.minor.project.service;

import com.example.minor.project.Entity.Book;
import com.example.minor.project.Entity.User;
import com.example.minor.project.exception.UserAlreadyExistException;
import com.example.minor.project.exception.UserNotFoundException;
import com.example.minor.project.repository.BookRepository;
import com.example.minor.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) throws UserNotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("No user found with id "+id));
    }

    public User save(User user) throws UserAlreadyExistException{
        Optional<User> isUserExist = userRepository.findById(user.getId());
        if(isUserExist.isPresent()){
            throw new UserAlreadyExistException("User already present with given Id "+user.getId());
        }
        return userRepository.save(user);
    }

    public void deleteById(Long id) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(id);

        if(user.isEmpty()){
            throw new UserNotFoundException("No user found with id "+id);
        }
        userRepository.deleteById(id);
    }
}
