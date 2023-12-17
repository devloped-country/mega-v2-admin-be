package com.app.mega.dto.response.note;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Builder
@Data
@ToString
public class NoteSendResponse {
    private String myName;
    private Long noteSendId;
}
