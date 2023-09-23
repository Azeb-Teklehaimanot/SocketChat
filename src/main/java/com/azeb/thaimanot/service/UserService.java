package com.azeb.thaimanot.service;

import com.azeb.thaimanot.entity.User;
import com.azeb.thaimanot.exceptions.InvalidCredentialsException;
import com.azeb.thaimanot.exceptions.UserNotFoundException;
import com.azeb.thaimanot.exceptions.UsernameAlreadyExistsException;
import com.azeb.thaimanot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(User user) {
        // Check if the username is unique (you can add more validations)
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new UsernameAlreadyExistsException(user.getUsername());
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User signIn(String username, String password) {
        // Find the user by username
        User user = userRepository.findByUsername(username);

        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }
        throw new InvalidCredentialsException();
    }

    public User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }
}
