package com.app.mega.mapper;

import com.app.mega.dto.request.PaymentReqDto;
import com.app.mega.dto.response.AttendanceResponse;
import com.app.mega.dto.response.PaymentResDto;
import com.app.mega.dto.response.UserResponse;
import com.app.mega.entity.Admin;
import com.app.mega.entity.Payment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface PaymentMapper {

    PaymentResDto requestTossPayment0();
    PaymentResDto requestTossPayment3();
    PaymentResDto requestTossPayment6();
    PaymentResDto requestTossPayment12();

//    List<AttendanceResponse> getAttendanceListByUserIdAndMonth(Long userId, int month);

    void requestTossPayment0(@Param("institutionId") Long institutionId,@Param("paymentReqDto") PaymentReqDto paymentReqDto, LocalDateTime currentPayTime,LocalDateTime NextPayTime);
    void requestTossPayment3(@Param("institutionId") Long institutionId,@Param("paymentReqDto") PaymentReqDto paymentReqDto, LocalDateTime currentPayTime,LocalDateTime NextPayTime);
    void requestTossPayment6(@Param("institutionId") Long institutionId,@Param("paymentReqDto") PaymentReqDto paymentReqDto, LocalDateTime currentPayTime,LocalDateTime NextPayTime);
    void requestTossPayment12(@Param("institutionId") Long institutionId,@Param("paymentReqDto") PaymentReqDto paymentReqDto, LocalDateTime currentPayTime,LocalDateTime NextPayTime);
    boolean existsByOrderId(@Param("orderId") String orderId);

}
