package com.app.mega.mapper;

import com.app.mega.dto.response.DashboardResponse;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface DashboardMapper {
  List<DashboardResponse> findAllUserTodayAttendanceByCourseId(@Param("id") Long id);
  List<DashboardResponse> findStatsTodayAttendanceByCourseId(@Param("id") Long id);
  List<DashboardResponse> findThisWeekAttendanceCountByCourseId(@Param("id") Long id);
  List<DashboardResponse> findThisWeekLateCountByCourseId(@Param("id") Long id);
}
