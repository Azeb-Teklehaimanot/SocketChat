package com.azeb.thaimanot.controller;

import com.azeb.thaimanot.exceptions.UsernameAlreadyExistsException;
import com.azeb.thaimanot.exceptions.InvalidCredentialsException;
import com.azeb.thaimanot.entity.User;
import com.azeb.thaimanot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            User registeredUser = userService.registerUser(user);
            return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
        } catch (UsernameAlreadyExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestParam String username, @RequestParam String password) {

        try {
            User user = userService.signIn(username, password);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (InvalidCredentialsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }
}
