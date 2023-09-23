package com.azeb.thaimanot.controller;

import com.azeb.thaimanot.dto.ChatRoomUserDto;
import com.azeb.thaimanot.dto.MessageDto;
import com.azeb.thaimanot.entity.Message;
import com.azeb.thaimanot.service.ChatRoomService;
import com.azeb.thaimanot.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private ChatRoomService chatRoomService;

    @MessageMapping("/send")
    public ResponseEntity<Message> sendMessage(@Payload MessageDto messageDto) {
            return ResponseEntity.ok(messageService.sendMessage(messageDto));
    }

    @MessageMapping("/joinRoom")
    public ResponseEntity<Object> joinRoom(@Payload ChatRoomUserDto chatRoomDto) {
        return ResponseEntity.ok(chatRoomService.addUserToChatRoom(chatRoomDto.getRoomId(), chatRoomDto.getUserId()));
    }

    @MessageMapping("/leaveRoom")
    public ResponseEntity<Object> leaveRoom(@Payload ChatRoomUserDto chatRoomDto) {
        return ResponseEntity.ok(chatRoomService.removeUserFromChatRoom(chatRoomDto.getRoomId(), chatRoomDto.getUserId()));
    }
}
