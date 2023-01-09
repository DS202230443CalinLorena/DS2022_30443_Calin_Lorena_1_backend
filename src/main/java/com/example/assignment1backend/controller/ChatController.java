package com.example.assignment1backend.controller;

import com.example.assignment1backend.model.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/message")
    @SendTo("/chatroom/public")
    public ChatMessage receiveMessage(@Payload ChatMessage chatMessage){
        return chatMessage;
    }

    @MessageMapping("/private-message")
    public ChatMessage recMessage(@Payload ChatMessage chatMessage){
        simpMessagingTemplate.convertAndSendToUser(chatMessage.getReceiverEmail(), "/private", chatMessage);
        return chatMessage;
    }
}
