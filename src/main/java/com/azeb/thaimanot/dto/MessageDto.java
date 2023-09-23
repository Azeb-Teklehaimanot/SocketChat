package com.azeb.thaimanot.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@NoArgsConstructor
public class MessageDto implements Serializable {
    private Long chatRoomId;
    private Long userId;
    private String content;
    private String fileUrl; // To handle file URL (optional)
    // Constructors, getters, and setters
}
