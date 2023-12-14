package com.app.mega.dto.response;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResDto {
//    private String payType;
    private Long amount;
    private String payName;
    private String orderId;
    private String adminEmail;
    private String adminName;
    private String successUrl;
    private String failUrl;
    private String failReason;
    private boolean cancelYN;
    private String cancelReason;
    private String createdAt;
}