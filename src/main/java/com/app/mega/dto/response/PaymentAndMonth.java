package com.app.mega.dto.response;

import com.app.mega.entity.Attendance;
import com.app.mega.entity.Course;
import com.app.mega.entity.Institution;
import com.app.mega.entity.UserNotification;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;
@Data
@AllArgsConstructor
public class PaymentAndMonth {

    private String paymentKey;
    private String orderId;
    private Long amount;
    private Long month;
}
