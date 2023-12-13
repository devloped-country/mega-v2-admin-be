package com.app.mega.service.mybatis;

import com.app.mega.dto.response.DashboardResponse;
import com.app.mega.mapper.DashboardMapper;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardService {
  private final DashboardMapper dashboardMapper;

  @Transactional
  public List<DashboardResponse> findAllAttendanceStat(Long courseId) {
    return dashboardMapper.findAllUserTodayAttendanceByCourseId(courseId);
  }

  @Transactional
  public List<DashboardResponse> findStatsTodayAttendance(Long courseId) {
    return dashboardMapper.findStatsTodayAttendanceByCourseId(courseId);
  }

  @Transactional
  public List<DashboardResponse> findThisWeekAttendanceCount(Long courseId) {
    return dashboardMapper.findThisWeekAttendanceCountByCourseId(courseId);
  }

  @Transactional
  public List<DashboardResponse> findThisWeekLateCount(Long courseId) {
    return dashboardMapper.findThisWeekLateCountByCourseId(courseId);
  }
}
