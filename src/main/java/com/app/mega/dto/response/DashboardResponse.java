package com.app.mega.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardResponse {
  private String userName;
  private String userPhone;
  private Integer applianceStatus;
  private LocalDateTime attendanceStartTime;
  private LocalDateTime attendanceEndTime;
  private Integer attendanceStatus;
  private LocalDate attendanceDate;
  private LocalDateTime applianceTime;
  private Integer count;
}
