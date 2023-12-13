package com.app.mega.service.jpa;

import com.app.mega.common.CommonResponse;
import com.app.mega.common.Constants;
import com.app.mega.common.handler.CustomException;
import com.app.mega.config.util.ses.EmailSender;
import com.app.mega.config.util.sns.SnsSender;
import com.app.mega.config.util.sns.SnsSenderDto;
import com.app.mega.dto.request.user.UserSaveRequest;
import com.app.mega.entity.Institution;
import com.app.mega.entity.User;
import com.app.mega.repository.CourseRepository;
import com.app.mega.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class UserManageService {
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final EmailSender emailSender;
    private final SnsSender snsSender;

    private static final String USER_WEB = "http://localhost:8082";

    //교육생 추가
    @Transactional
    public ResponseEntity userSave(UserSaveRequest request, Institution institution) throws Exception {
        System.out.println("request = " + request + ", institution = " + institution);
        System.out.println("UserManageService.userSave");
        //이미 추가한 유저인가
        User prevUser = userRepository.findByEmail(request.getEmail());
        if (prevUser != null) {
            throw new CustomException(Constants.ExceptionType.AUTHENTICATION, HttpStatus.BAD_REQUEST, "이미 추가한 교육생입니다.");
        }
        String firstPassword =  Integer.toString(ThreadLocalRandom.current().nextInt(100000, 1000000));

        // 유저를 db에 등록
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .password(passwordEncoder.encode(firstPassword)) // 비밀번호 인코딩
                .institution(institution)
                .isIdentified(false)
            .course(courseRepository.findByNameAndInstitution(request.getCourse(), institution))
                .build();
        userRepository.save(user);

        String institutionName = user.getInstitution().getName();
        String courseName = user.getCourse().getName();

        //문자발송
        String mobileNo = "+82"+user.getPhone();
        String smsTxt = "[MEGA] [" + institutionName+ "] 입니다. [" + courseName+"] 과정의 교육생으로 등록되었습니다.\n메일을 확인 후 본인인증을 완료하십시오.";
        SnsSenderDto dto = new SnsSenderDto(mobileNo, smsTxt);
        snsSender.send(dto);

        //메일발송
        ArrayList<String> to = new ArrayList<>();
        to.add(user.getEmail());
        String subject = "[" + institutionName+ "] [" + courseName+"]의 교육생으로 등록되었습니다.";
        String content = "아이디는 이메일주소입니다. \n임시비밀번호: " + firstPassword + "\n로그인 후 본인인증을 완료하십시오.";
        emailSender.send(subject, content, to);

        return ResponseEntity.status(HttpStatus.OK).body(
                CommonResponse.builder()
                        .responseCode(1)
                        .responseMessage("[성공] 교육생 추가 및 문자메일 발송완료")
                        .data(null)
                        .build());
    }
}
