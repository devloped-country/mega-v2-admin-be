package com.app.mega.dto.response.note;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AfterNoteSendResponse {
    private String myName;
    private Long noteSendId;
}
