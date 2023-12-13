package com.app.mega.dto.request.alarm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class AlarmIdRequest {
    List<Long> alarmIds;
}
