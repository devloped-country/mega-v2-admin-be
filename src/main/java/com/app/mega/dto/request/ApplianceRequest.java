package com.app.mega.dto.request;

import com.app.mega.entity.Attendance;
import com.app.mega.entity.User;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ApplianceRequest {
    private String reason;//dto
    private LocalDate applianceDate;
    //    private Attendance attendanceId;
//    private User userId;
    private Integer status;//공가공결 신청하는 사람의 원하는 상태값
//    public ApplianceRequest(){}
}
