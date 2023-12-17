package com.app.mega.controller;

import com.app.mega.common.CommonResponse;
import com.app.mega.dto.request.LocationRequest;
import com.app.mega.dto.request.user.UserSaveRequest;
import com.app.mega.dto.response.LocationResponse;
import com.app.mega.dto.response.NoticeResponse;
import com.app.mega.dto.response.UserInfoResponse;
import com.app.mega.dto.response.UserResponse;
import com.app.mega.entity.Admin;
import com.app.mega.service.jpa.InstitutionServiceJpa;
import com.app.mega.service.jpa.UserManageService;
import com.app.mega.service.jpa.UserServiceJpa;
import java.util.List;
import javax.xml.stream.Location;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserManageService userManageService;
    private final UserServiceJpa userServiceJpa;
    private final InstitutionServiceJpa institutionServiceJpa;

    // 유저등록 컨트롤러
    @PostMapping("/save_user")
    public ResponseEntity register(@RequestBody UserSaveRequest request, @AuthenticationPrincipal Admin admin) throws Exception {
        return userManageService.userSave(request, admin.getInstitution());
    }
    /*
    {
                    "name": "송정희",
                    "email": "sjh8924@naver.com",
                    "phone": "01033802064"
                    "course": ??
}
     */

    @PostMapping
    public ResponseEntity<CommonResponse<Boolean>> getLocation(@RequestBody
        LocationRequest locationRequest) {
        Boolean isCorrectLocation = userServiceJpa.findInstitutionIdByEmail(locationRequest);
        System.out.println(locationRequest);
        if(isCorrectLocation) {
            return ResponseEntity.status(HttpStatus.OK).body(
                CommonResponse.<Boolean>builder().responseCode(1).responseMessage("성공")
                    .data(isCorrectLocation).build());
        }

        return ResponseEntity.status(HttpStatus.OK).body(
            CommonResponse.<Boolean>builder().responseCode(-1).responseMessage("에러")
                .data(isCorrectLocation).build());
    }


    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<UserInfoResponse>> getUserInfo(@PathVariable("id") Long id) {
        UserInfoResponse userInfoResponse = userServiceJpa.findUserInfoByCourseId(id);

        return ResponseEntity.status(HttpStatus.OK).body(
            CommonResponse.<UserInfoResponse>builder().responseCode(1).responseMessage("성공")
                .data(userInfoResponse).build());
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<CommonResponse<List<UserResponse>>> getUsers(@PathVariable("courseId") Long courseId) {
        List<UserResponse> userResponses = userServiceJpa.findUsersByCourseId(courseId);

        return ResponseEntity.status(HttpStatus.OK).body(
            CommonResponse.<List<UserResponse>>builder().responseCode(1).responseMessage("성공")
                .data(userResponses).build());
    }
}
