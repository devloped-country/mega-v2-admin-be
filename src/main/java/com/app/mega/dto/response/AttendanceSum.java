package com.app.mega.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.app.mega.dto.response.AttendanceSum;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AttendanceSum {
    private String attendanceCount;
    private String tardinessCount;
    private String absenceCount;
    private String vacationCount;
}
