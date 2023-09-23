package com.azeb.thaimanot.controller;

import com.azeb.thaimanot.entity.ChatRoom;
import com.azeb.thaimanot.service.ChatRoomService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/chatrooms")
@Api(value = "Chat Room Management", tags = "ChatRoom Management")
public class ChatRoomController {

    @Autowired
    private ChatRoomService chatRoomService;

    @PostMapping("/create")
    @ApiOperation(value = "Create a new chatroom")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully created chatroom"),
            @ApiResponse(code = 400, message = "Invalid input, object invalid")
    })
    public ResponseEntity<ChatRoom> createChatroom(@RequestParam String chatRoomName, @RequestParam int chatRoomMaxSize) {
        return ResponseEntity.ok(chatRoomService.createChatRoom(chatRoomName, chatRoomMaxSize));
    }

    @GetMapping
    @ApiOperation(value = "Get all chatrooms")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 404, message = "Chatrooms not found")
    })
    public ResponseEntity<List<ChatRoom>> getChatRooms() {
        return ResponseEntity.ok(chatRoomService.getAllChatRooms());
    }
}
