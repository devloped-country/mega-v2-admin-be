package com.app.mega.dto.response.note;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TrashNoteResponse {
    Long id;
    String title;
    String content;
    String from;
}
