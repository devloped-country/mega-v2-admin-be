package com.app.mega.service.mybatis;

import com.app.mega.dto.request.ApplianceRequest;
import com.app.mega.dto.request.AttendanceRequest;
import com.app.mega.dto.response.ApplianceResponse;
import com.app.mega.dto.response.AttendanceResponse;
import com.app.mega.dto.response.AttendanceSum;
import com.app.mega.dto.response.UserResponse;
import com.app.mega.dto.request.ApplianceRequest;
import com.app.mega.entity.Attendance;
import com.app.mega.mapper.AttendanceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class AttendanceService {

    private final AttendanceMapper attendanceMapper;

    public UserResponse getUserInfo(Long id){
        return attendanceMapper.getUserInfo(id);}


    //public List<AttendanceResponse> getAttendanceListByUserIdAndMonth(Long userId, int month) {
//    return attendanceMapper.getAttendanceListByUserIdAndMonth(userId, month);
//}
    public List<AttendanceResponse> getAttendanceListByUserIdAndMonth(Long userId, int month) {
        List<AttendanceResponse> responses = attendanceMapper.getAttendanceListByUserIdAndMonth(userId, month);
        for (AttendanceResponse response : responses) {
            switch (response.getStatus()) {
                case 0:
                    response.setStatusDescription("미출석");
                    break;
                case 1:
                    response.setStatusDescription("출석");
                    break;
                case 2:
                    response.setStatusDescription("지각");
                    break;
                case 3:
                    response.setStatusDescription("조퇴");
                    break;
                case 4:
                    response.setStatusDescription("공가");
                    break;
                default:
                    response.setStatusDescription("Unknown");
            }
        }
        return responses;
    }
    public AttendanceSum calculateAttendanceSum(Long userId) {
        List<AttendanceResponse> attendanceList = attendanceMapper.getAttendanceListByUserId(userId);

        int attendanceCount = 0;
        int tardinessCount = 0;
        int absenceCount = 0;
        int vacationCount = 0;
        System.out.println("4444");

        for (AttendanceResponse attendance : attendanceList) {
            switch (attendance.getStatus()) {
                case 1:
                    attendanceCount++;
                    break;
                case 2:
                    tardinessCount++;
                    break;
                case 3:
                    absenceCount++;
                    break;
                case 4:
                    vacationCount++;
                    break;
//                //추가할것들
//                default:
            }
        }

        AttendanceSum attendanceSum = new AttendanceSum();
        attendanceSum.setAttendanceCount(String.valueOf(attendanceCount));
        attendanceSum.setTardinessCount(String.valueOf(tardinessCount));
        attendanceSum.setAbsenceCount(String.valueOf(absenceCount));
        attendanceSum.setVacationCount(String.valueOf(vacationCount));

        return attendanceSum;
    }

    public void saveChangeAttendanceStatus(Long userId,Integer changeStatus) {
        attendanceMapper.saveChangeAttendanceStatus(userId, changeStatus);
    }
    public AttendanceResponse getAttendanceResponse(@Param("attendanceId") Long attendanceId){
        return attendanceMapper.getAttendanceResponse(attendanceId);
    }


    public void saveAttendance(AttendanceRequest attendanceRequest) {
        System.out.println(attendanceRequest);
        attendanceMapper.saveAttendance(attendanceRequest);
    }

    public Long getApplianceStatus(Long id,LocalDate date) {
        return attendanceMapper.getApplianceStatus(id,date);
    }

    public void attendanceChangegAllow(AttendanceResponse attendanceResponse,Long changStatus) {
        attendanceMapper.attendanceChangegAllow(attendanceResponse, changStatus);
    }

    public void AttendanceAllowChangeYes(Long attendanceId,Integer status) {
        attendanceMapper.AttendanceAllowChangeYes(attendanceId, status);
    }

    public void ApplianceAllowChangeYse(Long attendanceId,Long id) {
        attendanceMapper.ApplianceAllowChangeYse(attendanceId, id);
    }

    public void AttendanceChangeNoRequest(Long attendanceId,Long id) {
        attendanceMapper.AttendanceChangeNoRequest(attendanceId,id);
    }
    public AttendanceService(AttendanceMapper attendanceMapper) {
        this.attendanceMapper = attendanceMapper;
    }

    public Long getAttendanceId(Long id,LocalDate date) {
        return attendanceMapper.getAttendanceId(id,date);
    }


    public List<UserResponse> getUserList() {
        return attendanceMapper.getUserList();
    }

    public CourseResponse getCourse(){return  attendanceMapper.getCourse();}


    public List<AttendanceResponse> attendanceList() {
        return attendanceMapper.attendanceList();
    }

    public List<ApplianceResponse> getAppliancesById(Long id) {
        return attendanceMapper.getAppliancesById(id);
    }

    public void deleteAttendance(Long id) {
        attendanceMapper.deleteAttendance(id);
    }

    public void createApplianceForAttendance(Long id, ApplianceRequest applianceRequest, Long attendanceId) {
        attendanceMapper.createApplianceForAttendance(id, applianceRequest, attendanceId);
    }
}