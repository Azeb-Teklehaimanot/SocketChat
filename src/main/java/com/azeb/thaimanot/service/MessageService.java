package com.azeb.thaimanot.service;

import com.azeb.thaimanot.dto.MessageDto;
import com.azeb.thaimanot.entity.ChatRoom;
import com.azeb.thaimanot.entity.Message;
import com.azeb.thaimanot.entity.User;
import com.azeb.thaimanot.exceptions.ChatRoomNotFoundException;
import com.azeb.thaimanot.repository.MessageRepository;
import com.azeb.thaimanot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private ChatRoomService chatRoomService;
    @Autowired
    private UserRepository userService;

    @Autowired
    private FileStorageService fileStorageService;

    private final SimpMessagingTemplate messagingTemplate;


    @Autowired
    public MessageService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public Message sendMessage(MessageDto messageDto) {
        ChatRoom chatRoom = chatRoomService.findChatRoomById(messageDto.getChatRoomId());
        User user = userService.findById(messageDto.getUserId()).orElseThrow(() -> new ChatRoomNotFoundException(messageDto.getUserId()));

        if (chatRoom != null && user != null) {
            boolean isUserInChatRoom = chatRoom.getUsers().stream()
                    .anyMatch(chatRoomUser -> chatRoomUser.getId().equals(user.getId()));

            if (isUserInChatRoom) {
                Message message = new Message();
                String textMessage = messageDto.getContent();

                // If file URL is present, it's either a video or an attachment.
                if (messageDto.getFileUrl() != null && !messageDto.getFileUrl().isEmpty()) {
                    textMessage += "\nFile URL : \n" + messageDto.getFileUrl();
                    // Assuming you can determine the type from the URL, or another field may be needed in the MessageDto.
                    Path filePath = Paths.get(messageDto.getFileUrl());
                    try {
                        String contentType = Files.probeContentType(filePath);
                        if (contentType.contains("video")) {
                            message.setType(Message.MessageType.VIDEO);
                        } else {
                            message.setType(Message.MessageType.ATTACHMENT);
                        }
                    } catch (IOException e) {
                        message.setType(Message.MessageType.ATTACHMENT);
                        throw new RuntimeException(e);
                    }

                    message.setFilePath(filePath.toFile().getAbsolutePath());
                } else {
                    message.setType(Message.MessageType.TEXT);
                }

                message.setChatRoom(chatRoom);
                message.setUser(user);
                message.setContent(textMessage);
                message.setCreatedAt(Instant.now());
                message.setUpdatedAt(Instant.now());

                try {


                    Message messageResponse = messageRepository.save(message);
                    String topic = "/topic/chatroom-" + message.getChatRoom().getId();
                    messagingTemplate.convertAndSend(topic, message);
                    return messageResponse;
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
