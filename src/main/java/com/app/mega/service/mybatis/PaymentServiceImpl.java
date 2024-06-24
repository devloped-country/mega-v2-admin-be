package com.app.mega.service.mybatis;
import com.app.mega.config.util.ses.EmailSender;
import com.app.mega.dto.request.PaymentReqDto;
import com.app.mega.mapper.PaymentMapper;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;


@Service
@Transactional
public class PaymentServiceImpl {
    private final PaymentMapper paymentMapper;
    private final EmailSender emailSender;

    public PaymentServiceImpl(PaymentMapper paymentMapper, EmailSender emailSender) {
        this.paymentMapper = paymentMapper;
        this.emailSender = emailSender;
    }

    public void requestTossPayment3(Long institutionId, PaymentReqDto paymentReqDto, LocalDateTime currentPayTime, LocalDateTime nextPayTime) {
        try {
            if (paymentMapper.existsByOrderId(paymentReqDto.getOrderId())) {
                throw new RuntimeException("Duplicate orderId");
            }
            paymentMapper.requestTossPayment3(institutionId, paymentReqDto, currentPayTime, nextPayTime);
            sendPaymentCheckAndRetry(paymentReqDto);
        } catch (Exception e) {
            throw new RuntimeException("결제를 실패하였습니다.", e);
        }
    }

    public void requestTossPayment6(Long institutionId, PaymentReqDto paymentReqDto, LocalDateTime currentPayTime, LocalDateTime nextPayTime) {
        try {
            if (paymentMapper.existsByOrderId(paymentReqDto.getOrderId())) {
                throw new RuntimeException("Duplicate orderId");
            }
            paymentMapper.requestTossPayment6(institutionId, paymentReqDto, currentPayTime, nextPayTime);
            sendPaymentCheckAndRetry(paymentReqDto);
        } catch (Exception e) {
            throw new RuntimeException("결제를 실패하였습니다.", e);
        }
    }


    public void requestTossPayment12(Long institutionId, PaymentReqDto paymentReqDto, LocalDateTime currentPayTime, LocalDateTime nextPayTime) {
        try {
            if (paymentMapper.existsByOrderId(paymentReqDto.getOrderId())) {
                throw new RuntimeException("Duplicate orderId");
            }
            paymentMapper.requestTossPayment12(institutionId, paymentReqDto, currentPayTime, nextPayTime);
            sendPaymentCheckAndRetry(paymentReqDto);
        } catch (Exception e) {
            throw new RuntimeException("결제를 실패하였습니다.", e);
        }
    }

    private void sendPaymentCheckAndRetry(PaymentReqDto paymentReqDto) {
        int retryCount = 0;
        while (retryCount < 3) {
            try {
                String subject = "결제 확인 ";
                String content = "결제가 성공적으로 처리되었습니다. 결제 금액: " + paymentReqDto.getAmount();
                List<String> toAddresses = Collections.singletonList(paymentReqDto.getEmail());

                emailSender.send(subject, content, toAddresses);
                return; // 메일 전송 성공 시 종료
            } catch (Exception e) {
                retryCount++;
                if (retryCount == 3) {
                    throw new RuntimeException("Failed to send email after 3 attempts", e);
                }
            }
        }
    }
}