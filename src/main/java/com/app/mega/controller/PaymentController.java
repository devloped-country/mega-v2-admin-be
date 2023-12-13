package com.app.mega.controller;

import com.app.mega.config.TossPaymentConfig;
import com.app.mega.dto.PaymentDto;
import com.app.mega.dto.PaymentFailDto;
import com.app.mega.dto.response.PaymentResDto;
import com.app.mega.dto.response.SingleResponse;
import com.app.mega.entity.Admin;
import com.app.mega.entity.Payment;
import com.app.mega.entity.User;
import com.app.mega.mapper.PaymentMapper;
import com.app.mega.service.mybatis.PaymentServiceImpl;
import jakarta.validation.Valid;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping("/api/v1/payments")
public class PaymentController {
    private final PaymentServiceImpl paymentService;
    private final TossPaymentConfig tossPaymentConfig;
    private final PaymentMapper paymentMapper;

    public PaymentController(PaymentServiceImpl paymentService, TossPaymentConfig tossPaymentConfig, PaymentMapper paymentMapper) {
        this.paymentService = paymentService;
        this.tossPaymentConfig = tossPaymentConfig;
        this.paymentMapper = paymentMapper;
    }
    //    @AuthenticationPrincipal User user
    @PostMapping("/toss")
    public ResponseEntity requestTossPayment(@RequestBody @Valid PaymentDto paymentReqDto) {
        User user = new User();
        user.setName("김유범");
        user.setEmail("kimub1204@naver.com");
        user.setPhone("01086341449");
        user.setIsIdentified(true);
//        PaymentResDto paymentResDto = paymentService.equestTossPayment(paymentReqDto.toEntity(), user.getName()).toPaymentResDto();
        Payment payment = paymentReqDto.toEntity();
        PaymentResDto paymentResDto = paymentService.requestTossPayment(payment, user.getName()).toPaymentResDto();
        paymentResDto.setSuccessUrl(paymentReqDto.getYourSuccessUrl() == null ? tossPaymentConfig.getSuccessUrl() : paymentReqDto.getYourSuccessUrl());
        paymentResDto.setFailUrl(paymentReqDto.getYourFailUrl() == null ? tossPaymentConfig.getFailUrl() : paymentReqDto.getYourFailUrl());
        return ResponseEntity.ok().body(new SingleResponse<>(paymentResDto));
    }
    @PostMapping("/toss/success")
    public ResponseEntity tossPaymentSuccess(
            @RequestParam String paymentKey,
            @RequestParam String orderId,
            @RequestParam Long amount
    ) throws JSONException {

        return ResponseEntity.ok().body(new SingleResponse<>(paymentService.tossPaymentSuccess(paymentKey, orderId, amount)));
    }

}