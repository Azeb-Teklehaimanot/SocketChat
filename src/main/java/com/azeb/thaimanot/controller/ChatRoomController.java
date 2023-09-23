package com.azeb.thaimanot.controller;

import com.azeb.thaimanot.entity.ChatRoom;
import com.azeb.thaimanot.service.ChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/chatrooms")
public class ChatRoomController {

    @Autowired
    private ChatRoomService chatRoomService;

    @PostMapping("/create")
    public ResponseEntity<ChatRoom> createChatroom(@RequestParam String chatRoomName, @RequestParam int chatRoomMaxSize) {
        return ResponseEntity.ok(chatRoomService.createChatRoom(chatRoomName, chatRoomMaxSize));
    }

    @GetMapping
    public ResponseEntity<List<ChatRoom>> getChatRooms() {
        return ResponseEntity.ok(chatRoomService.getAllChatRooms());
    }
}
