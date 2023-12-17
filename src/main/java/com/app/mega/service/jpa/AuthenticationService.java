package com.app.mega.service.jpa;

import com.app.mega.common.CommonResponse;
import com.app.mega.common.Constants;
import com.app.mega.common.handler.CustomException;
import com.app.mega.config.util.ses.EmailSender;
import com.app.mega.config.util.sns.SnsSender;
import com.app.mega.config.util.sns.SnsSenderDto;
import com.app.mega.dto.request.admin.AdminSaveRequest;
import com.app.mega.dto.request.admin.AuthenticationRequest;
import com.app.mega.dto.request.admin.InstitutionSaveRequest;
import com.app.mega.dto.response.admin.AuthenticationResponse;
import com.app.mega.dto.response.admin.ManagerAndCourseResponse;
import com.app.mega.entity.Admin;
import com.app.mega.entity.Course;
import com.app.mega.entity.Institution;
import com.app.mega.entity.Payment;
import com.app.mega.repository.AdminRepository;
import com.app.mega.repository.CourseRepository;
import com.app.mega.repository.InstitutionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final CourseRepository courseRepository;
    private final AdminRepository adminRepository;
    private final InstitutionRepository institutionRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final EmailSender emailSender;
    private final SnsSender snsSender;

    private static final String ADMIN_WEB = "http://localhost:8081";

    Map<String, Integer> certification = new HashMap<>();
    private int certificationNumber;

    //기관 회원가입
    @Transactional
    public AuthenticationResponse registerInstitution(InstitutionSaveRequest request) throws Exception {
        //이미 추가된 기관 관리자인가
        Admin prevAdmin = adminRepository.findByEmail(request.getEmail());
        if (prevAdmin != null) {
            throw new CustomException(Constants.ExceptionType.AUTHENTICATION, HttpStatus.BAD_REQUEST, "User already Exists");
        }

        //db에 등록 (기관)
        Institution institution = Institution.builder()
            .name(request.getName())
            .latitude(request.getLatitude())
            .longitude(request.getLongitude())
            .address(request.getAddress())
            .build();
        institutionRepository.save(institution);

        //db에 등록(과정)
        for(String courseName:request.getCourses()) {
            Course course = Course.builder()
                .name(courseName)
                .institution(institution)
                .build();
            courseRepository.save(course);
        }

        // db에 등록 (관리자)
        Admin admin = Admin.builder()
            .name(request.getName())
            .email(request.getEmail())
            .phone(request.getPhone())
            .password(passwordEncoder.encode(request.getPassword())) // 비밀번호 인코딩
            .institution(institution)
            .isManager(false)
            .build();
        adminRepository.save(admin);

        String jwtToken = jwtService.generateToken(admin);
        return AuthenticationResponse.builder()
            .token(jwtToken)
                .isManager(false)
                .id(admin.getId())
            .build();
    }

    //기관이 매니저 등록
    @Transactional
    public ResponseEntity register(AdminSaveRequest request, Institution institution) throws Exception {
        //이미 추가된 매니저인가
        Admin prevAdmin = adminRepository.findByEmail(request.getEmail());
        if (prevAdmin != null) {
            throw new CustomException(Constants.ExceptionType.AUTHENTICATION, HttpStatus.BAD_REQUEST, "User already Exists");
        }
        String rawPassword = request.getPassword();
        // db에 등록
        Admin admin = Admin.builder()
            .name(request.getName())
            .email(request.getEmail())
            .phone(request.getPhone())
            .password(passwordEncoder.encode(rawPassword)) // 비밀번호 인코딩
            .institution(institution)
            .isManager(true)
            .build();
        adminRepository.save(admin);

        //문자발송
        String mobileNo = "+82"+admin.getPhone();
        String smsTxt = "[MEGA] " + institution.getName() + "의 매니저로 등록되었습니다. 메일을 확인해주세요.";
        SnsSenderDto dto = new SnsSenderDto(mobileNo, smsTxt);
        snsSender.send(dto);

        //메일발송
        ArrayList<String> to = new ArrayList<>();
        to.add(admin.getEmail());
        String subject = "[MEGA] " + admin.getName() + "님, "+institution.getName() + "의 매니저로 등록되었습니다. 계정을 확인하십시오.";
        String content = "아이디는 이메일주소입니다. \n임시비밀번호: "+rawPassword+"\n"+ADMIN_WEB+"\n로그인을 완료하십시오.";
        emailSender.send(subject, content, to);

        return ResponseEntity.status(HttpStatus.OK).body(
            CommonResponse.builder()
                .responseCode(1)
                .responseMessage("[성공] 매니저 추가, 문자메일 발송완료")
                .data(null)
                .build());
    }
    //로그인
    @Transactional
    public AuthenticationResponse authenticate(AuthenticationRequest request){
        // 인증 시도. 인증에 실패하면 AuthenticationError 반환됨
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
                        )
                    );

         // 인증 성공 시
        Admin admin = adminRepository.findByEmail(request.getEmail());
        String jwtToken = jwtService.generateToken(admin);

        List<Payment> paymentList = admin.getInstitution().getPayment();
        if(paymentList != null) {
            LocalDate lastPayTime = null; // 가장 마지막 LocalDate 값을 저장할 변수 초기화

            for (Payment payment : paymentList) {
                LocalDateTime getNextPayTime = payment.getNextPayTime();
                LocalDate paymentNextDate = getNextPayTime.toLocalDate(); // LocalDateTime을 LocalDate로 변환

                if (lastPayTime == null || paymentNextDate.isAfter(lastPayTime)) {
                    lastPayTime = paymentNextDate;
                }
            }
            System.out.println(lastPayTime);

            LocalDateTime nowDateTime = LocalDateTime.now();
            LocalDate now = nowDateTime.toLocalDate();
            long n = ChronoUnit.DAYS.between(now, lastPayTime);

            if (n <= 7) {
                ArrayList<String> to = new ArrayList<>();
                to.add(admin.getEmail());
                String subject = "MEGA V2 구독갱신일 메일입니다.";
                String content = "어플 구독갱신 " + n + "일전 입니다 ";
                emailSender.send(subject, content, to);

            }
        }
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .isManager(admin.getIsManager())
                    .id(admin.getId())
                    .build();

    }


    //기관이 과정 등록
    @Transactional
    public ResponseEntity addCourse(List<String> courseNames, Institution institution) {

        for(String courseName : courseNames) {
            Course course = Course.builder()
                .name(courseName)
                .institution(institution)
                .build();
            courseRepository.save(course);
        }

        return ResponseEntity.status(HttpStatus.OK).body(
            CommonResponse.builder()
                .responseCode(1)
                .responseMessage("[성공] 과정 추가 완료")
                .data(courseNames)
                .build());
    }

    //인증번호 확인
    @Transactional
    public ResponseEntity certificate(String email, int certificationNumber) throws CustomException {
        System.out.println(certification.get(email));
        System.out.println(certificationNumber);
        if(certification.get(email) == certificationNumber) {
            return ResponseEntity.status(HttpStatus.OK).body(
                CommonResponse.builder()
                    .responseCode(1)
                    .responseMessage("[성공] 인증번호 일치")
                    .data(email)
                    .build());
        } else {
            throw new CustomException(Constants.ExceptionType.AUTHENTICATION, HttpStatus.BAD_REQUEST, "[실패] 인증번호 불일치");
        }
    }

    //인증번호 이메일발송
    @Transactional
    public ResponseEntity identify(String email){
        System.out.println(certification);
        if(certification.get(email) != null) {
            System.out.println("다시요청");
            certification.remove(email);
        }
        if (adminRepository.findByEmail(email) == null) {
            certificationNumber =  ThreadLocalRandom.current().nextInt(100000, 1000000);
            certification.put(email, certificationNumber);

            ArrayList<String> to = new ArrayList<>();
            to.add(email);
            String subject = "MEGA 인증메일입니다.";
            String content = "어플에 인증번호를 입력해주세요.인증번호: "+certificationNumber;

            emailSender.send(subject, content, to);
            System.out.println("인증번호"+certificationNumber);
            return ResponseEntity.status(HttpStatus.OK).body(
                CommonResponse.builder()
                    .responseCode(1)
                    .responseMessage("[성공] 인증메일 전송 완료")
                    .data(email)
                    .build());
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(
                CommonResponse.builder()
                    .responseCode(-1)
                    .responseMessage("[실패] 이미 가입된 이메일입니다.")
                    .data(email)
                    .build());
        }
    }

    @Transactional
    public ManagerAndCourseResponse readManagerAndCourse(Institution institution) {
        Map<Long,String> courseInfo = new HashMap<>();
        Map<String, String> managerInfo = new HashMap<>();

        for(Course course:institution.getCourseList()) {
            courseInfo.put(course.getId(), course.getName());
        }
        for(Admin admin : institution.getAdmin()) {
            if(Boolean.TRUE.equals(admin.getIsManager())) {
                managerInfo.put(admin.getName(), admin.getEmail());
            }
        }

        return ManagerAndCourseResponse.builder()
            .managerInfo(managerInfo)
            .courseInfo(courseInfo)
            .build();
    }
}