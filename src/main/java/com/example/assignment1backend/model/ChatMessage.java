package com.example.assignment1backend.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ChatMessage {
    private String senderEmail;
    private String receiverEmail;
    private String message;
    private ChatStatus status;
}
