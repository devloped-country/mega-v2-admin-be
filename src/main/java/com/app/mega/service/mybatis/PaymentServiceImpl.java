package com.app.mega.service.mybatis;

import com.app.mega.dto.PaymentDto;
import com.app.mega.dto.PaymentSuccessDto;
import com.app.mega.dto.request.PaymentReqDto;
import com.app.mega.dto.response.PaymentAndMonth;
import com.app.mega.dto.response.PaymentResDto;
import com.app.mega.entity.Payment;
import com.app.mega.config.TossPaymentConfig;
import com.app.mega.entity.Admin;
//import com.yata.backend.domain.member.service.MemberService;
//import com.yata.backend.domain.payment.config.TossPaymentConfig;
//import com.yata.backend.domain.payment.dto.PaymentSuccessDto;
//import com.yata.backend.domain.payment.entity.Payment;
//import com.yata.backend.domain.payment.repository.JpaPaymentRepository;
//import com.yata.backend.global.exception.CustomLogicException;
//import com.yata.backend.global.exception.ExceptionCode;

import com.app.mega.mapper.PaymentMapper;
import org.springframework.stereotype.Service;


import com.app.mega.repository.JpaPaymentRepository;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;


@Service
@Transactional
public class PaymentServiceImpl {

    private final PaymentMapper paymentMapper;

    private final JpaPaymentRepository paymentRepository;
    private final UserService userService;
    private final TossPaymentConfig tossPaymentConfig;

    public PaymentServiceImpl(PaymentMapper paymentMapper, JpaPaymentRepository paymentRepository, UserService userService, TossPaymentConfig tossPaymentConfig) {
        this.paymentMapper = paymentMapper;
        this.paymentRepository = paymentRepository;
        this.userService = userService;
        this.tossPaymentConfig = tossPaymentConfig;
    }

    public void requestTossPayment3(Long institutionId, PaymentReqDto paymentReqDto, LocalDateTime currentPayTime, LocalDateTime NextPayTime){
      paymentMapper.requestTossPayment3(institutionId,paymentReqDto,currentPayTime,NextPayTime);
    };

    public void requestTossPayment6(Long institutionId, PaymentReqDto paymentReqDto, LocalDateTime currentPayTime, LocalDateTime NextPayTime){
        paymentMapper.requestTossPayment6(institutionId,paymentReqDto,currentPayTime,NextPayTime);
    };

    public void requestTossPayment12(Long institutionId, PaymentReqDto paymentReqDto, LocalDateTime currentPayTime, LocalDateTime NextPayTime){
        paymentMapper.requestTossPayment12(institutionId,paymentReqDto,currentPayTime,NextPayTime);
    };
    ///////////////////////////////////////////////////////////
//
//
//    public PaymentReqDto requestTossPayment(Admin admin, PaymentReqDto paymentReqDto) {
//
//        //로그인한 admin으로부터 기관id가져와서 담기
//        Long institutionId = admin.getInstitution().getId();
////        UserResponse user = userService.findUserByEmail(institutionId);
////        if (paymentReqDto.getAmount() < 1000) {
////            throw new CustomLogicException(ExceptionCode.INVALID_PAYMENT_AMOUNT);
////        }
//        PaymentAndMonth paymentAndMonth = new PaymentAndMonth(paymentReqDto.getPaymentKey(),
//                paymentReqDto.getOrderId(), paymentReqDto.getAmount(),institutionId);
//
//
//
//        System.out.println("여기가 출력된다면 save 문제");
//        return
//    }
    //------------------------------------------------------------
//    public Payment requestTossPayment(Payment payment, String userEmail) {
//        UserResponse user = userService.findUserByEmail(userEmail);
////        if (payment.getAmount() < 1000) {
////            throw new CustomLogicException(ExceptionCode.INVALID_PAYMENT_AMOUNT);
////        }
//        payment.setUser(user.toEntity());
//        System.out.println("여기가 출력된다면 save 문제");
//        return paymentRepository.save(payment);
//    }

    public PaymentSuccessDto tossPaymentSuccess(String paymentKey, String orderId, Long amount, Admin admin) {

        PaymentSuccessDto successDto = new PaymentSuccessDto();
        successDto.setPaymentKey(paymentKey);
        successDto.setOrderId(orderId);
        successDto.setAmount(amount);
//        successDto.setMonth(month);
        successDto.setInstitutionId(admin.getInstitution().getId());
        return successDto;

//                Payment requestTossPayment(Payment payment, String userEmail);
//        PaymentSuccessDto tossPaymentSuccess(String paymentKey, String orderId, Long amount);

//   return
    }
//
//    @Transactional
//    public PaymentSuccessDto tossPaymentSuccess(String paymentKey, String orderId, Long amount) {
//        Payment payment = verifyPayment(orderId, amount);
//        PaymentSuccessDto result = requestPaymentAccept(paymentKey, orderId, amount);
//        payment.setPaymentKey(paymentKey);
//        payment.setPaySuccessYN(true);
//        memberService.updateMemberCache(payment.getCustomer());
//        return result;
//    }
    @Transactional
    public void tossPaymentFail(String code, String message, String orderId) {
//        Payment payment = paymentRepository.findByOrderId(orderId));
//        payment.setPaySuccessYN(false);
//        payment.setFailReason(message);
    }
}


//    public PaymentSuccessDto tossPaymentSuccess(String paymentKey, String orderId, Long amount, Long month, Admin admin) {
//
//        PaymentSuccessDto successDto = new PaymentSuccessDto();
//        successDto.setPaymentKey(paymentKey);
//        successDto.setOrderId(orderId);
//        successDto.setAmount(amount);
//        successDto.setMonth(month);
//        successDto.setInstitutionId(admin.getInstitution().getId());
//        return successDto;//내가만들던거