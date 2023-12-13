package com.app.mega.repository;

import com.app.mega.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceAuthRepository extends JpaRepository<Attendance, Long> {

}
