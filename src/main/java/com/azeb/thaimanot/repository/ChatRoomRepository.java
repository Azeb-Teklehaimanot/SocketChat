package com.azeb.thaimanot.repository;

import com.azeb.thaimanot.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    // Custom queries if needed
}
