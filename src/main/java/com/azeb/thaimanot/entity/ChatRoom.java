package com.azeb.thaimanot.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@Table(name = "chat_room")
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // Maximum capacity of the chat room
    private int maxSize = 250;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "chatroom_user",
            joinColumns = @JoinColumn(name = "chatroom_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @JsonIgnore
    private Set<User> users = new HashSet<>();

    @OneToMany(mappedBy = "chatRoom")
    @JsonIgnore
    private Set<Message> messages = new HashSet<>();


    @Override
    public int hashCode() {
        return Objects.hash(id, name, maxSize);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChatRoom chatRoom = (ChatRoom) o;
        return id.equals(chatRoom.id) &&
                name.equals(chatRoom.name) &&
                maxSize == chatRoom.maxSize;
    }
}
