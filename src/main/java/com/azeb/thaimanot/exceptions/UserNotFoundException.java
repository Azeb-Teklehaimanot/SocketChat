package com.azeb.thaimanot.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long userId) {
        super("User not found with ID: " + userId);
    }
}
