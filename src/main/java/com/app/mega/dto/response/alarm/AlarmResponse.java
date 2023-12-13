package com.app.mega.dto.response.alarm;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Builder
@AllArgsConstructor
public class AlarmResponse {
    String type;
    boolean isRead;
}
