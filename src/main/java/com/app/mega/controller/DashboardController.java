package com.app.mega.controller;

import com.app.mega.dto.response.DashboardResponse;
import com.app.mega.service.mybatis.DashboardService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class DashboardController {

  private final DashboardService dashboardService;

  @GetMapping("/{id}/status")
  public List<DashboardResponse> readAllAttendanceStat(@PathVariable("id") Long id) {
    return dashboardService.findAllAttendanceStat(id);
  }

  @GetMapping("/{id}/stats")
  public List<DashboardResponse> readStatsTodayAttendance(@PathVariable("id") Long id) {
    return dashboardService.findStatsTodayAttendance(id);
  }

  @GetMapping("/{id}/attendance")
  public List<DashboardResponse> readThisWeekAttendanceCount(@PathVariable("id") Long id) {
    return dashboardService.findThisWeekAttendanceCount(id);
  }

  @GetMapping("/{id}/late")
  public List<DashboardResponse> readThisWeekLateCount(@PathVariable("id") Long id) {
    return dashboardService.findThisWeekLateCount(id);
  }
}
