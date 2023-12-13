package com.app.mega.dto.response;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import com.app.mega.dto.response.AttendanceSum;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceResponse {
    private Long id; //유저아이디번호
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer status;
    private String statusDescription; // New field for descriptive status
    private LocalDate attendanceDate;

}
