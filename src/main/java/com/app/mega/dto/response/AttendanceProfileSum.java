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
//List<AttendanceProfileSum> : 과정 1개당 모든 학생정보
//AttendanceProfileSum = UserResponse(학생테이블) + List<AttendanceResponse>(학생)
//AttendanceResponse: 출석정보
//