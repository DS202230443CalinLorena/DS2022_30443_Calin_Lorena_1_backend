package com.example.assignment1backend.model;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TextMessageDto {
    private String message;
    private Long correspondingUserId;
    private Long deviceId;
    private Timestamp timestamp;
}
