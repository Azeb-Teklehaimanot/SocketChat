package com.azeb.thaimanot.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class ChatRoomDto implements Serializable {
    private String chatRoomName;
    private int chatRoomMaxSize;
    // Constructors, getters, and setters
}
