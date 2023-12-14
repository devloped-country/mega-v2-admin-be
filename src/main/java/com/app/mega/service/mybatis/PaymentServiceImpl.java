package com.app.mega.service.mybatis;

import com.app.mega.dto.PaymentSuccessDto;
import com.app.mega.entity.Payment;
import com.app.mega.config.TossPaymentConfig;
import com.app.mega.dto.PaymentSuccessDto;
import com.app.mega.dto.response.UserResponse;
import com.app.mega.entity.Admin;
//import com.yata.backend.domain.member.service.MemberService;
//import com.yata.backend.domain.payment.config.TossPaymentConfig;
//import com.yata.backend.domain.payment.dto.PaymentSuccessDto;
//import com.yata.backend.domain.payment.entity.Payment;
//import com.yata.backend.domain.payment.repository.JpaPaymentRepository;
//import com.yata.backend.global.exception.CustomLogicException;
//import com.yata.backend.global.exception.ExceptionCode;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collections;
import java.util.Map;
import com.app.mega.config.TossPaymentConfig;
import com.app.mega.entity.Payment;
import com.app.mega.repository.JpaPaymentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import com.app.mega.dto.PaymentSuccessCardDto;
import com.app.mega.service.mybatis.PaymentService;



@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {
    private final JpaPaymentRepository paymentRepository;
    private final UserService userService;
    private final TossPaymentConfig tossPaymentConfig;

    public PaymentServiceImpl(JpaPaymentRepository paymentRepository, UserService userService, TossPaymentConfig tossPaymentConfig) {
        this.paymentRepository = paymentRepository;
        this.userService = userService;
        this.tossPaymentConfig = tossPaymentConfig;
    }

    public Payment requestTossPayment(Payment payment, String userEmail) {
        UserResponse user = userService.findUserByEmail(userEmail);
//        if (payment.getAmount() < 1000) {
//            throw new CustomLogicException(ExceptionCode.INVALID_PAYMENT_AMOUNT);
//        }
        payment.setUser(user.toEntity());
        System.out.println("여기가 출력된다면 save 문제");
        return paymentRepository.save(payment);
    }
    ////////////////////!!!!!!!!!!!!!!!///////////////////
    public PaymentSuccessDto tossPaymentSuccess(String paymentKey, String orderId, Long amount) {
        PaymentSuccessDto successDto = new PaymentSuccessDto();
        successDto.setPaymentKey(paymentKey);
        successDto.setOrderId(orderId);
        successDto.setAmount(amount);
//        successDto.setMessage("Payment successful"); // You can customize this message

        return successDto;

        //        Payment requestTossPayment(Payment payment, String userEmail);
//        PaymentSuccessDto tossPaymentSuccess(String paymentKey, String orderId, Long amount);
//
//   return
    }
}