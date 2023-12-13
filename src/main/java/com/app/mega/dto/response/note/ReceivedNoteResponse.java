package com.app.mega.dto.response.note;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReceivedNoteResponse {
    Long id;
    String title;
    String content;
    String from;
    Boolean isRead;
    LocalDateTime time;
}
