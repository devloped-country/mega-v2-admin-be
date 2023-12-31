package com.app.mega.service.mybatis;
import com.app.mega.dto.PaymentSuccessDto;
import com.app.mega.entity.Payment;
import com.app.mega.dto.PaymentSuccessDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface PaymentService {
    Payment requestTossPayment(Payment payment);
    PaymentSuccessDto tossPaymentSuccess(String paymentKey, String orderId, Long amount);
//    PaymentSuccessDto requestPaymentAccept(String paymentKey, String orderId, Long amount);
//    Slice<Payment> findAllChargingHistories(String username, Pageable pageable);
//    Payment verifyPayment(String orderId, Long amount);
}

