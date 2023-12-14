package com.app.mega.service.mybatis;

import com.amazonaws.services.dynamodbv2.xspec.L;
import com.app.mega.dto.request.ApplianceRequest;
import com.app.mega.dto.request.AttendanceRequest;
import com.app.mega.dto.request.CourseRequest;
import com.app.mega.dto.response.*;
import com.app.mega.dto.request.ApplianceRequest;
import com.app.mega.entity.Attendance;
import com.app.mega.entity.Course;
import com.app.mega.entity.Institution;
import com.app.mega.entity.User;
import com.app.mega.mapper.AttendanceMapper;
import com.app.mega.repository.CourseRepository;
import com.app.mega.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final UserRepository userRepository;
    private final AttendanceMapper attendanceMapper;
//    public AttendanceService(AttendanceMapper attendanceMapper) {
//        this.attendanceMapper = attendanceMapper;
//    }

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

    public Long getAttendanceId(Long id,LocalDate date) {
        return attendanceMapper.getAttendanceId(id,date);
    }


    public List<UserResponse> getUserList() {
        return attendanceMapper.getUserList();
    }

    public List<CourseResponse> getCourse(){return  attendanceMapper.getCourse();}

    public List<UserResponse> getUserListByCourse(Long courseId) {
        return attendanceMapper.getUserListByCourse(courseId);
    }

//    public Map<Long, List<UserResponse>>  getUserListByCourse(Institution institution) {
//        Map<Long, List<UserResponse>> userListByCourse = new HashMap<>();
//        List<UserResponse> userResponseList = new ArrayList<>();
//        List<Course> courseList = institution.getCourseList();
//        for(Course course : courseList) {
//            List<User> userList = userRepository.findAllByCourse(course);
//            for(User user : userList) {
//                UserResponse userResponse = UserResponse.builder()
//                        .id(user.getId())
//                        .name(user.getName())
//                        .email(user.getEmail())
//                        .phone(user.getPhone())
//                        .isSigned(user.getIsIdentified())
//                        .build();
//                userResponseList.add(userResponse);
//            }
//            userListByCourse.put(course.getId(), userResponseList);
//        }
//        return userListByCourse;
//    }

    public List<UserResponse> getUserListById(Long id) {
        return attendanceMapper.getUserListById(id);
    }
//    public List<AttendanceResponse> attendanceList() {
//        return attendanceMapper.attendanceList();
//    }

    public List<ApplianceResponse> getAppliancesById(Long id) {


        return attendanceMapper.getAppliancesById(id);
    }
    public List<AttendanceResponse> getAttendanceById(Long id) {
        return attendanceMapper.getAttendanceById(id);
    }

    public void deleteAttendance(Long id) {
        attendanceMapper.deleteAttendance(id);
    }

    public void createApplianceForAttendance(Long id, ApplianceRequest applianceRequest, Long attendanceId) {
        attendanceMapper.createApplianceForAttendance(id, applianceRequest, attendanceId);
    }
}