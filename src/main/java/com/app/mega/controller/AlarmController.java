package com.app.mega.controller;

import com.app.mega.dto.request.alarm.AlarmIdRequest;
import com.app.mega.dto.response.alarm.AlarmResponse;
import com.app.mega.entity.Admin;
import com.app.mega.service.jpa.AlarmService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/alarm")
@CrossOrigin(origins = "*")
public class AlarmController {
    AlarmService alarmService;

    //알림 저장하기: 서비스로만 구현해서 다른 메서드들에 추가하는게 좋을듯?

    //알림함 불러오기
    @GetMapping ("/list")
    public List<AlarmResponse> readAlarm(@AuthenticationPrincipal Admin admin) {
        return null;
    }

    //알림함 읽음처리
    @PutMapping ("/read")
    public void readAlarm(AlarmIdRequest alarmIdRequest) {
        alarmService.readAlarm(alarmIdRequest.getAlarmIds());
    }

    //알림삭제
    @DeleteMapping("/delete")
    public void delete(AlarmIdRequest alarmIdRequest) {
        alarmService.delete(alarmIdRequest.getAlarmIds());
    }

}
