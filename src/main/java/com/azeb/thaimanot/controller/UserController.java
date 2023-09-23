package com.azeb.thaimanot.controller;

import com.azeb.thaimanot.exceptions.UsernameAlreadyExistsException;
import com.azeb.thaimanot.exceptions.InvalidCredentialsException;
import com.azeb.thaimanot.entity.User;
import com.azeb.thaimanot.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/users")
@Api(value = "User Management", tags = "User Management")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    @ApiOperation(value = "Register a new user")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "User registered successfully"),
            @ApiResponse(code = 400, message = "Username already exists"),
            @ApiResponse(code = 500, message = "Server error")
    })
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            User registeredUser = userService.registerUser(user);
            return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
        } catch (UsernameAlreadyExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/signin")
    @ApiOperation(value = "Sign in a user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User signed in successfully"),
            @ApiResponse(code = 401, message = "Invalid credentials"),
            @ApiResponse(code = 500, message = "Server error")
    })
    public ResponseEntity<?> signIn(@RequestParam String username, @RequestParam String password) {

        try {
            User user = userService.signIn(username, password);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (InvalidCredentialsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }
}
