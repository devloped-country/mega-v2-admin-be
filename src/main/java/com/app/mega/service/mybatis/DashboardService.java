package com.app.mega.service.mybatis;

import com.app.mega.dto.response.DashboardResponse;
import com.app.mega.mapper.DashboardMapper;
import jakarta.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
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
    List<DashboardResponse> existingResponses = dashboardMapper.findThisWeekAttendanceCountByCourseId(courseId);

    List<DashboardResponse> missingResponses = new ArrayList<>();

    LocalDate weekStart = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
    LocalDate weekEnd = weekStart.plus(4, ChronoUnit.DAYS);

    List<LocalDate> expectedDates = weekStart.datesUntil(weekEnd.plusDays(1)).collect(Collectors.toList());

    for (LocalDate expectedDate : expectedDates) {
      boolean dateExists = existingResponses.stream()
          .anyMatch(response -> response.getAttendanceDate().equals(expectedDate));

      if (!dateExists) {
        // Create a DashboardResponse for the missing date
        DashboardResponse missingResponse = new DashboardResponse();
        missingResponse.setAttendanceDate(expectedDate);
        missingResponses.add(missingResponse);
      }
    }

    List<DashboardResponse> mergedList = new ArrayList<>();
    mergedList.addAll(existingResponses);
    mergedList.addAll(missingResponses);

    Collections.sort(mergedList);

    return mergedList;
  }

  @Transactional
  public List<DashboardResponse> findThisWeekLateCount(Long courseId) {
    List<DashboardResponse> existingResponses = dashboardMapper.findThisWeekLateCountByCourseId(courseId);
    List<DashboardResponse> missingResponses = new ArrayList<>();

    LocalDate weekStart = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
    LocalDate weekEnd = weekStart.plus(4, ChronoUnit.DAYS);

    List<LocalDate> expectedDates = weekStart.datesUntil(weekEnd.plusDays(1)).collect(Collectors.toList());

    for (LocalDate expectedDate : expectedDates) {
      boolean dateExists = existingResponses.stream()
          .anyMatch(response -> response.getAttendanceDate().equals(expectedDate));

      if (!dateExists) {
        // Create a DashboardResponse for the missing date
        DashboardResponse missingResponse = new DashboardResponse();
        missingResponse.setAttendanceDate(expectedDate);
        missingResponses.add(missingResponse);
      }
    }

    List<DashboardResponse> mergedList = new ArrayList<>();
    mergedList.addAll(existingResponses);
    mergedList.addAll(missingResponses);

    Collections.sort(mergedList);

    return mergedList;
  }
}
