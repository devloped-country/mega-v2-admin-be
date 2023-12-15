package com.app.mega.dto;

import com.app.mega.entity.Payment;
import lombok.*;

import java.util.UUID;
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDto {
//    @NonNull
//    private PayType payType;
    @NonNull
    private Long amount;
    @NonNull
    private String orderName;
//    private String yourSuccessUrl;
//    private String yourFailUrl;
    private String payName;


    public Payment toEntity() {
        return Payment.builder()
//                .payType(payType)
                .amount(amount)
                .orderId(UUID.randomUUID().toString())
                .paySuccessYN(false)
                .payName(payName)
                .build();
    }
}