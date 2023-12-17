package com.app.mega.controller;

import com.app.mega.common.handler.CustomException;
import com.app.mega.dto.request.CertificationRequest;
import com.app.mega.dto.request.IdentifyRequest;
import com.app.mega.dto.request.admin.*;
import com.app.mega.dto.request.course.AddCourseRequest;
import com.app.mega.dto.response.admin.AuthenticationResponse;
import com.app.mega.dto.response.admin.ManagerAndCourseResponse;
import com.app.mega.entity.Admin;
import com.app.mega.service.jpa.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    // 기관 회원가입
    @PostMapping("/register/institution")
    public ResponseEntity<AuthenticationResponse> registerInstitution(
        @RequestBody InstitutionSaveRequest request) throws Exception {
        return ResponseEntity.ok(authenticationService.registerInstitution(request));
        /*
            {
                "latitude": 5.2,
                "longitude": 10.3,
                "address": "ㅇㅇㅇ",
                "name": "메가존클라우드 교육센터",
                "courses":["데브옵스 운영관리","클라우드 네이티브 개발"],
                "email": "mega@mega.com",
                "password": "123",
                "phone": "12345678910"
            }
        */

    }

    //인증번호 이메일발송
    @PostMapping("/identify")
    public ResponseEntity identify(@RequestBody IdentifyRequest request) throws CustomException {
        return authenticationService.identify(request.getEmail());
    }

    //인증번호 확인
    @PostMapping("/identify/certificate")
    public ResponseEntity certificate(@RequestBody CertificationRequest request) throws CustomException {
            return authenticationService.certificate(request.getEmail(), request.getCertificationNumber());
    }

    // 기관이 매니저 등록
    @PostMapping("/register/manager")
    public ResponseEntity registerManager(
        @RequestBody AdminSaveRequest request, @AuthenticationPrincipal Admin admin) throws Exception {
        return authenticationService.register(request, admin.getInstitution());
    }

    // 기관이 과정 등록
    @PostMapping("/register/course")
    public ResponseEntity registerCourse(
        @RequestBody AddCourseRequest request, @AuthenticationPrincipal Admin admin) throws Exception {
        return authenticationService.addCourse(request.getCourseNames(), admin.getInstitution());
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(
        @RequestBody AuthenticationRequest request
    ) {
        System.out.println(request);
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    //기관이 매니저,과정 불러오기
    @GetMapping("/read/manager_course")
    public ResponseEntity<ManagerAndCourseResponse> readManagerAndCourse(@AuthenticationPrincipal Admin admin) {
        return ResponseEntity.ok(authenticationService.readManagerAndCourse(admin.getInstitution()));
    }

    @GetMapping("/checklogin")
    public int checklogin() {
        return 1;
    }
}