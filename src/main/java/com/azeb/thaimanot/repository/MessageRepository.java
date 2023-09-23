package com.azeb.thaimanot.repository;

import com.azeb.thaimanot.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    // Custom queries if needed, e.g., find messages by chat room
}
