package com.app.mega.dto.response.note;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@AllArgsConstructor
@ToString
@Builder
@Data
public class ReceiverResponse {
    Long id;
    String name;
    String email;
}
