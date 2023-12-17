    package com.app.mega.controller;
    import com.app.mega.dto.request.*;
    import com.app.mega.dto.response.*;
    import com.app.mega.entity.Admin;
    import com.app.mega.entity.Attendance;
    import com.app.mega.entity.Course;
    import com.app.mega.entity.User;
    import com.app.mega.repository.AttendanceRepository;
    import com.app.mega.service.mybatis.AttendanceService;
    import com.app.mega.service.mybatis.UserService;
    import jakarta.validation.Valid;
    import lombok.Getter;
    import lombok.RequiredArgsConstructor;
    import lombok.Setter;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.scheduling.annotation.Scheduled;
    import org.springframework.security.core.annotation.AuthenticationPrincipal;
    import org.springframework.web.bind.annotation.*;
    import com.app.mega.dto.response.AttendanceSum;

    import java.time.LocalDate;
    import java.time.LocalDateTime;
    import java.time.LocalTime;
    import java.time.temporal.ChronoUnit;
    import java.util.ArrayList;
    import java.util.HashMap;
    import java.util.List;
    import java.util.Map;
    import java.util.stream.Collectors;

    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RestController;

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
    import org.springframework.web.servlet.function.EntityResponse;

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

        @PostMapping("/toss3")
        public void requestTossPayment3(@AuthenticationPrincipal Admin admin, @RequestBody @Valid PaymentReqDto paymentReqDto) {
    //   [ paymentReqDto 안에 paymentKey/orderId/amount/yourSuccessUrl/yourFailUrl ]
            Long institutionId = admin.getInstitution().getId();
    //        PaymentResDto paymentResDto = paymentService.requestTossPayment(admin,paymentReqDto);
            LocalDateTime currentPayTime = LocalDateTime.now(); // 현재 날짜와 시간 가져오기
            LocalDateTime NextPayTime = currentPayTime.plus(3, ChronoUnit.MONTHS); // 3개월 뒤의 날짜 계산
    //
            paymentService.requestTossPayment3(institutionId,paymentReqDto,currentPayTime,NextPayTime);
    //        return ResponseEntity.ok().body(new SingleResponse<>(paymentResDto));//!!!

        }

        @PostMapping("/toss6")
        public void requestTossPayment6(@AuthenticationPrincipal Admin admin, @RequestBody @Valid PaymentReqDto paymentReqDto) {
    //   [ paymentReqDto 안에 paymentKey/orderId/amount/yourSuccessUrl/yourFailUrl ]
            Long institutionId = admin.getInstitution().getId();
    //        PaymentResDto paymentResDto = paymentService.requestTossPayment(admin,paymentReqDto);
            LocalDateTime currentPayTime = LocalDateTime.now(); // 현재 날짜와 시간 가져오기
            LocalDateTime NextPayTime = currentPayTime.plus(6, ChronoUnit.MONTHS); // 3개월 뒤의 날짜 계산
    //
            paymentService.requestTossPayment3(institutionId,paymentReqDto,currentPayTime,NextPayTime);
    //        return ResponseEntity.ok().body(new SingleResponse<>(paymentResDto));//!!!

        }

        @PostMapping("/toss12")
        public void requestTossPayment12(@AuthenticationPrincipal Admin admin, @RequestBody @Valid PaymentReqDto paymentReqDto) {
    //   [ paymentReqDto 안에 paymentKey/orderId/amount/yourSuccessUrl/yourFailUrl ]
            Long institutionId = admin.getInstitution().getId();
    //        PaymentResDto paymentResDto = paymentService.requestTossPayment(admin,paymentReqDto);
            LocalDateTime currentPayTime = LocalDateTime.now(); // 현재 날짜와 시간 가져오기
            LocalDateTime NextPayTime = currentPayTime.plus(12, ChronoUnit.MONTHS); // 3개월 뒤의 날짜 계산
    //
            paymentService.requestTossPayment3(institutionId,paymentReqDto,currentPayTime,NextPayTime);
    //        return ResponseEntity.ok().body(new SingleResponse<>(paymentResDto));//!!!

        }
    }


//
//    @GetMapping("/toss/success")
//    public ResponseEntity tossPaymentSuccess(
//            @RequestParam String paymentKey,
//            @RequestParam String orderId,
//            @RequestParam Long amount,
//            @AuthenticationPrincipal Admin admin
//    ) {
//
//        return ResponseEntity.ok().body(new SingleResponse<>(paymentService.tossPaymentSuccess(paymentKey, orderId, amount,admin)));
//    }
//
//    @GetMapping("/toss/fail")
//    public ResponseEntity tossPaymentFail(
//            @RequestParam String code,
//            @RequestParam String message,
//            @RequestParam String orderId
//    )
//    {
//        paymentService.tossPaymentFail(code, message, orderId);
//        return ResponseEntity.ok().body(new SingleResponse<>(
//                PaymentFailDto.builder()
//                        .errorCode(code)
//                        .errorMessage(message)
//                        .orderId(orderId)
//                        .build()
//        ));
//    }




  //////////////////////////////////////////////////////////////////////////////////////////////////
//
////걀제 개월수 정해서 정해진 금액으로 결제요청하고 성공,실패 나뉘고 성공시 3가지값+개월수를 담은 paymentDTO를 만들어줌
//    @PostMapping("/toss")
//    public ResponseEntity requestTossPayment(@AuthenticationPrincipal Admin admin) {
//        paymentService.requestTossPayment(PaymentDto paymentReqDto, )
//
//        Payment paymentEt = paymentReqDto.toEntity();
//        /////////////////////!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//        PaymentResDto paymentResDto = paymentService.requestTossPayment(paymentEt, user.getEmail()).toPaymentResDto();
//        paymentResDto.setSuccessUrl(paymentReqDto.getYourSuccessUrl() == null ? tossPaymentConfig.getSuccessUrl() : paymentReqDto.getYourSuccessUrl());
//        paymentResDto.setFailUrl(paymentReqDto.getYourFailUrl() == null ? tossPaymentConfig.getFailUrl() : paymentReqDto.getYourFailUrl());
//        return ResponseEntity.ok().body(new SingleResponse<>(paymentResDto));
//    }
//
////성공시 페이지 이동하면서 다시 요청보낼건데 3가지값이 담긴 객체와 해당 개월수+기관아이디 를 Payment테이블에 저장
//    @GetMapping("/toss/success/{month}")
//    public ResponseEntity tossPaymentSuccess(
//            @PathVariable Long month,
//            @RequestParam String paymentKey,
//            @RequestParam String orderId,
//            @RequestParam Long amount,
//            @AuthenticationPrincipal Admin admin
//    ) throws JSONException {
//        return ResponseEntity.ok().body
//                (new SingleResponse<>
//                        (paymentService.tossPaymentSuccess
//                                (paymentKey, orderId, amount, month, admin)));
//    }
//}
//--------------------------------------------
//    @PostMapping("/toss")
//    public ResponseEntity requestTossPayment(@RequestBody @Valid PaymentDto paymentReqDto) {
//        User user = new User();
//        user.setName("메가");
//        user.setEmail("sjh8924@naver.com");
//        user.setPhone("01033802064");
//        user.setIsIdentified(true);
//        PaymentResDto paymentResDto = paymentService.RequestTossPayment(paymentReqDto.toEntity(), user.getName()).toPaymentResDto();
//
//-----------------------------------------------