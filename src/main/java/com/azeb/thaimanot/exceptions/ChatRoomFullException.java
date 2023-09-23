package com.azeb.thaimanot.exceptions;

public class ChatRoomFullException extends RuntimeException {
    public ChatRoomFullException(String chatRoomName, int maxCapacity) {
        super("Chat room '" + chatRoomName + "' is full. Maximum capacity is " + maxCapacity + " users.");
    }
}
