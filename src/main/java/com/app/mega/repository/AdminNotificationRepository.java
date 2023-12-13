package com.app.mega.repository;

import com.app.mega.entity.AdminNotification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminNotificationRepository extends JpaRepository<AdminNotification, Long> {
}
