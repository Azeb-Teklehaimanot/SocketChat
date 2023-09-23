package com.azeb.thaimanot.exceptions;

public class ChatRoomNotFoundException extends RuntimeException {
    public ChatRoomNotFoundException(Long chatRoomId) {
        super("Chat room not found with ID: " + chatRoomId);
    }
}
