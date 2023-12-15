package com.app.mega.mapper;

import com.app.mega.dto.request.ApplianceRequest;
import com.app.mega.dto.request.AttendanceRequest;
import com.app.mega.dto.request.CourseRequest;
import com.app.mega.dto.response.ApplianceResponse;
import com.app.mega.dto.response.AttendanceResponse;
import com.app.mega.dto.response.CourseResponse;
import com.app.mega.dto.response.UserResponse;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface AttendanceMapper {
    List<AttendanceResponse> getAttendanceListByUserIdAndMonth(Long userId, int month);
    UserResponse getUserInfo(Long id);
    void saveChangeAttendanceStatus(Long userId,Integer changeStatus);
    void AttendanceAllowChangeYes(Long attendanceId,Integer status);
    void ApplianceAllowChangeYse(Long attendanceId,Long id);

    void AttendanceChangeNoRequest(Long attendanceId,Long id);
    List<AttendanceResponse> getAttendanceListByUserId(Long userId);

    List<CourseResponse> getCourse();

    List<UserResponse> getUserList();
    List<UserResponse> getUserListByCourse(Long courseId);
    List<UserResponse> getUserListById(Long id);
    void saveAttendance(AttendanceRequest attendanceRequest);

    AttendanceResponse getAttendanceResponse(Long attendanceId) ;

    Long getApplianceStatus(@Param("id") Long id, @Param("date") LocalDate date);

    void attendanceChangegAllow(@Param("attendanceResponse") AttendanceResponse attendanceResponse, @Param("changStatus") Long changStatus);
    List<AttendanceResponse> attendanceList();

    List<ApplianceResponse> getAppliancesById(Long id);
    List<AttendanceResponse> getAttendanceById(Long id);

    Long getAttendanceId(Long id, LocalDate date);

    void createApplianceForAttendance(Long id, ApplianceRequest applianceRequest, Long attendanceId);

    void getAttendanceByIdAndTime(Long id , ApplianceRequest dto, LocalDate date);

    void updateAttendance(@Param("id") Long id, @Param("attendanceResponse") AttendanceResponse attendanceResponse);

    void deleteAttendance(Long id);


}
