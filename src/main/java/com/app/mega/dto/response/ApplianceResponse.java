package com.app.mega.dto.response;

import com.app.mega.entity.Attendance;
import com.app.mega.entity.User;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApplianceResponse {
    private String reason;
    private LocalDate applianceDate= LocalDate.now();//dto
    //    private Attendance attendanceId;
//    private User userId;
    private Integer status;
}
