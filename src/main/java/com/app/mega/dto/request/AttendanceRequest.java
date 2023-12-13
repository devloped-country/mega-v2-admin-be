package com.app.mega.dto.request;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
@ToString
@Data
public class AttendanceRequest {
    private Long id;//이건 출석id번호가 아닌 교육생id번호이다
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer status;
    private LocalDate attendanceDate;
    public AttendanceRequest(Long id, LocalDateTime startTime, LocalDateTime endTime, Integer status,LocalDate attendanceDate) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
        this.attendanceDate=attendanceDate;
    }
}
