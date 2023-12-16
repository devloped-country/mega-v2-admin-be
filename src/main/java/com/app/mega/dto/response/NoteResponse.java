package com.app.mega.dto.response;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class NoteResponse {
    private String content;
    private String from;
    private String to;
    private String time;
    private String title;
}
