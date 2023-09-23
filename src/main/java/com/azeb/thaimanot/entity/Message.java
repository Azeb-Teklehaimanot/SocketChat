package com.azeb.thaimanot.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.Instant;
//import java.time.Instant;

@Entity
@Data
@NoArgsConstructor
@Table(name = "message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "chatroom_id")
    private ChatRoom chatRoom;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String content;

    private String filePath;

    private MessageType type;

    private Instant createdAt;


    private Instant updatedAt;

    public enum MessageType {
        TEXT, VIDEO, ATTACHMENT
    }
}
