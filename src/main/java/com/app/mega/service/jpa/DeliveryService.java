package com.app.mega.service.jpa;

import com.app.mega.entity.Attendance;
import com.app.mega.entity.User;
import com.app.mega.repository.AttendanceRepository;
import com.app.mega.repository.UserRepository;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoZonedDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.amazon.awssdk.services.sqs.model.Message;

@Service
@RequiredArgsConstructor
public class DeliveryService {

  private final AwsDynamoDbService awsDynamoDbService;
  private final AttendanceRepository attendanceRepository;
  private final UserRepository userRepository;

  @Transactional
  public void delivery(Message message) {
    String userQr = message.messageAttributes().get("QR").stringValue();
    String createdTime = message.messageAttributes().get("CreatedTime").stringValue();
    String email = message.messageAttributes().get("Email").stringValue();

    String qr = awsDynamoDbService.readQr();
    Instant instant = Instant.ofEpochMilli(Long.parseLong(createdTime));
    User user = userRepository.findByEmail(email);

    Attendance attendance = attendanceRepository.findByUserIdAndAttendanceDate(user.getId(),
        LocalDate.now());
    ZonedDateTime midnightUTCPlus9 = ZonedDateTime.of(LocalDateTime.now().toLocalDate(),
        LocalTime.MIDNIGHT, ZoneId.of("UTC+9"));

    if (attendance.getStartTime().atZone(ZoneId.of("UTC+9")).toInstant()
        .compareTo(midnightUTCPlus9.toInstant()) != 0) {
      checkoutAttendance(user.getId());
      return;
    }

    ZonedDateTime nowUTCMinus9 = ZonedDateTime.now(ZoneId.of("UTC-9"));
    LocalDateTime currentDateUTCMinus9 = nowUTCMinus9.toLocalDate().atStartOfDay();
    LocalDateTime baseStartTime = currentDateUTCMinus9.withHour(9).withMinute(0).withSecond(0)
        .withNano(0);

    if (userQr.equals(qr) && instant.atZone(ZoneId.of("Asia/Seoul"))
        .isBefore(baseStartTime.atZone(ZoneId.of("Asia/Seoul"))) || instant.atZone(
        ZoneId.of("Asia/Seoul")).isEqual(baseStartTime.atZone(ZoneId.of("Asia/Seoul")))) {
      checkinAttendance(user.getId(), 1);
    } else if (userQr.equals(qr) && instant.atZone(ZoneId.of("Asia/Seoul"))
        .isAfter(baseStartTime.atZone(ZoneId.of("Asia/Seoul")))) {
      checkinAttendance(user.getId(), 2);
    }
  }

  @Transactional
  public void checkoutAttendance(Long userId) {
    Attendance attendance = attendanceRepository.findById(userId)
        .orElseThrow(IllegalArgumentException::new);
    ZonedDateTime koreaZonedDateTime = ZonedDateTime.ofInstant(Instant.now(),
        ZoneId.of("Asia/Seoul"));
    attendance.setEndTime(koreaZonedDateTime.toLocalDateTime());
  }

  @Transactional
  public void checkinAttendance(Long userId, Integer status) {
    Attendance attendance = attendanceRepository.findById(userId)
        .orElseThrow(IllegalArgumentException::new);
    attendance.setStatus(status);
    ZonedDateTime koreaZonedDateTime = ZonedDateTime.ofInstant(Instant.now(),
        ZoneId.of("Asia/Seoul"));
    attendance.setStartTime(koreaZonedDateTime.toLocalDateTime());
  }
}
