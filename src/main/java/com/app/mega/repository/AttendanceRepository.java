package com.app.mega.repository;

import com.app.mega.entity.Attendance;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    Attendance findByUserIdAndAttendanceDate(Long id, LocalDate attendanceDate);
}
