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
            Long institutionId = admin.getInstitution().getId();
            LocalDateTime currentPayTime = LocalDateTime.now(); // 현재 날짜와 시간 가져오기
            LocalDateTime NextPayTime = currentPayTime.plus(3, ChronoUnit.MONTHS); // 3개월 뒤의 날짜 계산

            paymentService.requestTossPayment3(institutionId,paymentReqDto,currentPayTime,NextPayTime);

        }

        @PostMapping("/toss6")
        public void requestTossPayment6(@AuthenticationPrincipal Admin admin, @RequestBody @Valid PaymentReqDto paymentReqDto) {
            Long institutionId = admin.getInstitution().getId();
            LocalDateTime currentPayTime = LocalDateTime.now(); // 현재 날짜와 시간 가져오기
            LocalDateTime NextPayTime = currentPayTime.plus(6, ChronoUnit.MONTHS); // 3개월 뒤의 날짜 계산

            paymentService.requestTossPayment6(institutionId,paymentReqDto,currentPayTime,NextPayTime);

        }

        @PostMapping("/toss12")
        public void requestTossPayment12(@AuthenticationPrincipal Admin admin, @RequestBody @Valid PaymentReqDto paymentReqDto) {
            Long institutionId = admin.getInstitution().getId();
            LocalDateTime currentPayTime = LocalDateTime.now(); // 현재 날짜와 시간 가져오기
            LocalDateTime NextPayTime = currentPayTime.plus(12, ChronoUnit.MONTHS); // 3개월 뒤의 날짜 계산

            paymentService.requestTossPayment12(institutionId,paymentReqDto,currentPayTime,NextPayTime);

        }
    }
