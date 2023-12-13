package com.app.mega.dto.response;


import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class AttendanceProfileSum {
    //    private Long id;//교육생id
    private UserResponse userResponse;
    private List<AttendanceResponse> AttendanceResponse;
    private AttendanceSum attendanceSum;
    private Map<Integer, Long> statusCountMap;//추가

}
