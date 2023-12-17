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

    //    void saveChangeAttendanceStatus(Long userId,Integer changeStatus);
//    void AttendanceAllowChangeYes(Long attendanceId,Integer status);
//    void ApplianceAllowChangeYse(Long attendanceId,Long id);
//
//    void AttendanceChangeNoRequest(Long attendanceId,Long id);

//    default List<ChargingHistoryDto> chargingHistoryToChargingHistoryResponses(List<Payment> chargingHistories) {
//        if (chargingHistories == null) {
//            return null;
//        }
//
//        return chargingHistories.stream()
//                .map(chargingHistory -> {
//                    return ChargingHistoryDto.builder()
//                            .paymentHistoryId(chargingHistory.getPaymentId())
//                            .amount(chargingHistory.getAmount())
//                            .orderName(chargingHistory.getOrderName())
////                            .createdAt(chargingHistory.getCreatedAt())
//                            .isPaySuccessYN(chargingHistory.isPaySuccessYN())
//                            .build();
//                }).collect(Collectors.toList());
//    }
}
