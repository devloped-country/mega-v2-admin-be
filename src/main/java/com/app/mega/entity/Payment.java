package com.app.mega.entity;

import com.app.mega.dto.PayType;
import com.app.mega.dto.response.PaymentResDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.domain.Auditable;

import java.time.temporal.TemporalAccessor;
import java.util.Optional;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Table
//        (indexes = {
//        @Index(name = "idx_payment_member", columnList = "admin"),
//        @Index(name = "idx_payment_paymentKey", columnList = "paymentKey"),
//})
public class Payment implements Auditable {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "payment_id", nullable = false, unique = true)
//    private Long paymentId;
//    @Column(nullable = false, name = "pay_type")
//    @Enumerated(EnumType.STRING)
//    private PayType payType;
//    @Column(nullable = false, name = "pay_amount")
//    private Long amount;
//    @Column(nullable = false, name = "pay_name")
//    private String orderName;
//    @Column(nullable = false, name = "order_id")
//    private String orderId;
//    private boolean paySuccessYN;
//    @ManyToOne(cascade = CascadeType.PERSIST)
//    @JoinColumn(name = "user")
//    private User user;
//    @Column
//    private String paymentKey;
//    @Column
//    private String failReason;
//
//    @Column
//    private boolean cancelYN;
//    @Column
//    private String cancelReason;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id", unique = true)
    private Long paymentId;

    @ColumnDefault("100")
    private Long amount;

    private String payName;

    private String orderId;

    @Column(columnDefinition = "TINYINT(1)")
    private Boolean paySuccessYN;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user")//user_id인지 확인하기
    private User user;

    private String paymentKey;

    private String failReason;

    @Column(columnDefinition = "TINYINT(1)")
    private Boolean cancelYN;

    private String cancelReason;

    public PaymentResDto toPaymentResDto() {
        return PaymentResDto.builder()
//                .payType(payType.getDescription())
                .amount(amount)
                .payName(payName)
                .orderId(orderId)
                .adminEmail(user.getEmail())
                .adminName(user.getName())
//                .createdAt(String.valueOf(getCreatedAt()))
                .cancelYN(cancelYN)
                .failReason(failReason)
                .build();
    }


    @Override
    public Optional getCreatedBy() {
        return Optional.empty();
    }

    @Override
    public void setCreatedBy(Object createdBy) {

    }

    @Override
    public Optional getCreatedDate() {
        return Optional.empty();
    }

    @Override
    public void setCreatedDate(TemporalAccessor creationDate) {

    }

    @Override
    public Optional getLastModifiedBy() {
        return Optional.empty();
    }

    @Override
    public void setLastModifiedBy(Object lastModifiedBy) {

    }

    @Override
    public Optional getLastModifiedDate() {
        return Optional.empty();
    }

    @Override
    public void setLastModifiedDate(TemporalAccessor lastModifiedDate) {

    }

    @Override
    public Object getId() {
        return null;
    }

    @Override
    public boolean isNew() {
        return false;
    }
}