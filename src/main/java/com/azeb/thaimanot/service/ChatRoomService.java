package com.azeb.thaimanot.service;

import com.azeb.thaimanot.entity.ChatRoom;
import com.azeb.thaimanot.entity.User;
import com.azeb.thaimanot.exceptions.ChatRoomFullException;
import com.azeb.thaimanot.exceptions.ChatRoomNotFoundException;
import com.azeb.thaimanot.repository.ChatRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatRoomService {

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private UserService userService;

    public ChatRoom createChatRoom(String chatRoomName, int chatRoomMaxSize) {
        ChatRoom chatroom = new ChatRoom();
        chatroom.setName(chatRoomName);
        chatroom.setMaxSize(chatRoomMaxSize);
        return chatRoomRepository.save(chatroom);
    }

    public ChatRoom addUserToChatRoom(Long chatRoomId, Long userId) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new ChatRoomNotFoundException(chatRoomId));
        if (chatRoom.getUsers().size() >= chatRoom.getMaxSize()) {
            throw new ChatRoomFullException(chatRoom.getName(), chatRoom.getMaxSize());
        }
        User user = userService.findUserById(userId);
        chatRoom.getUsers().add(user);
        return chatRoomRepository.save(chatRoom);
    }

    public ChatRoom removeUserFromChatRoom(Long chatRoomId, Long userId) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new ChatRoomNotFoundException(chatRoomId));

        User user = userService.findUserById(userId);
        chatRoom.getUsers().remove(user);
        return chatRoomRepository.save(chatRoom);
    }

    public List<ChatRoom> getAllChatRooms() {
        return chatRoomRepository.findAll();
    }

    public ChatRoom findChatRoomById(Long chatRoomId) {
        return chatRoomRepository.findById(chatRoomId).orElseThrow(() -> new ChatRoomNotFoundException(chatRoomId));
    }
}
