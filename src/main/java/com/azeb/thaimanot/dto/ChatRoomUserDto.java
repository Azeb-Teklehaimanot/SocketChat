package com.azeb.thaimanot.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class ChatRoomUserDto implements Serializable {
    private Long roomId;
    private Long userId;
    // Constructors, getters, and setters
}
