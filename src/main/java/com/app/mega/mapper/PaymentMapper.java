package com.app.mega.mapper;

import com.app.mega.entity.Payment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface PaymentMapper {



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
