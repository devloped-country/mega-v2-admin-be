package com.app.mega.service.jpa;

import com.app.mega.entity.Admin;
import com.app.mega.entity.AdminNotification;
import com.app.mega.repository.AdminNotificationRepository;
import com.app.mega.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlarmService {
    private final AdminNotificationRepository adminNotificationRepository;

    //알림 읽음처리
    public void setIsRead(List<Long> alarmIds) {
        for(Long alarmId : alarmIds) {
            AdminNotification alarm = adminNotificationRepository.findById(alarmId).get();
            alarm.setIsRead(true);
            adminNotificationRepository.save(alarm);
        }
    }


    public void delete(List<Long> alarmIds) {
    }

    public void readAlarm(List<Long> alarmIds) {
    }
}
