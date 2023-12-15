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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@CrossOrigin(origins = "http://localhost:3000")

@Slf4j
@Getter
@Setter
@RestController
@RequestMapping("/api/attendance")
@RequiredArgsConstructor
public class AttendanceController {

        private final AttendanceService attendanceService;
        private final UserService userService;
//-------------------------------------------------------

//원래 사용중 1일 한번
//
//        private LocalDateTime currentDateTime = LocalDateTime.now(); // currentDate를 클래스 필드로 선언
//        private LocalDate currentDate= LocalDate.now();
//
//        @Scheduled(cron = "0/9 * * * * ?") // 매 5초마다 실행
//        public void createDailyAttendanceForAllUsers() {
//                // currentDate를 1일씩 증가
//                currentDateTime = currentDateTime.plusDays(-1).with(LocalTime.of(0, 0, 0));;
//                currentDate = currentDate.plusDays(-1);
//
//
//                //0이면 오늘 지금
//                //+1이면 다음낳부터 1+
//
//                List<UserResponse> userList = attendanceService.getUserList();
//
//                for (UserResponse user : userList) {
//                        System.out.println(user);
//                        AttendanceRequest attendanceRequest = new AttendanceRequest(user.getId(),currentDateTime, currentDateTime, 2, currentDate);
//                        attendanceService.saveAttendance(attendanceRequest);
//                }
//
//        }
//-------------------------------------------------------
//--------------------------------------------------------------------
//[ 0미출,1출석,2지각,3결석,4공결 ]
//    start_time이 9:00이전이라면 status->1
//    start_time이 9:00이후라면   status->2
//    end_time이 17:00안이 아니면  status->3

        @Scheduled(cron = "0 5 17 * * ?")
        //(cron = "* * * 1 * ?") ->매일09시17시체크
        public void DailyAttendanceCheck() {
                List<UserResponse> userList = attendanceService.getUserList();

                for (UserResponse user : userList) {

                        Long attendanceId =user.getId();
                        LocalDateTime nineAM = LocalDateTime.of(LocalDate.now(), LocalTime.of(9, 0));
                        LocalDateTime fivePM = LocalDateTime.of(LocalDate.now(), LocalTime.of(17, 0));
////            LocalTime currentTime = LocalTime.now();//여기서 해당유저의 시작값,끝값을 가져와서 아래 현제시간에 넣긱ㄱ

                        AttendanceResponse attendanceResponse = attendanceService.getAttendanceResponse(attendanceId);
                        LocalDateTime startTime =attendanceResponse.getStartTime();
                        LocalDateTime endTime = attendanceResponse.getEndTime();
                        Integer changeStatus;

                        if (startTime.isBefore(nineAM)) {
                                changeStatus = 1; // Start time is before 9:00 AM
                        } else if (startTime.isAfter(nineAM) && startTime.isBefore(fivePM)) {
                                changeStatus = 2; // Start time is after 9:00 AM and before 5:00 PM
                        } else if (endTime.isBefore(fivePM)) {
                                changeStatus = 3; // End time is before 5:00 PM
                        } else {
                                changeStatus = 4; // Default status or handle other cases
                        }
                        //ㄴ>>여기부분을 조금만 로직수정하면 될듯//
//            AttendanceRequest attendanceRequest = new AttendanceRequest(user.getId(), attendanceResponse.getStartTime(), attendanceResponse.getEndTime(), changeStatus, LocalDate.now());
                        attendanceService.
                                saveChangeAttendanceStatus(user.getId(),changeStatus);
                }
        }


        //✅✅[과정명,번호 가져오기]
        @GetMapping("/getCourse")
        public List<CourseResponse> getCourse(@AuthenticationPrincipal Admin admin ) {


                return attendanceService.getCourse();
        }
        //=>admin , 기관ID에 맞는 교육과정들 가져옴

//-------------------------------------------------------------------
        //✅✅[[해당 교육과정 => 유저전체의 개인정보들(유저번호,이름,프로필) ]]

        //-> id.name,imageUrl,phone
//        @GetMapping("/getUserListByCourse")
//        public List<UserResponse> getUserListByCourse
//        (@AuthenticationPrincipal Admin admin) {
//                return attendanceService.getUserListByCourse(admin.getInstitution());
//        }

        @GetMapping("/{courseId}/getUserListByCourse")
        public List<UserResponse> getUserListByCourse(@PathVariable Long courseId)
        {return attendanceService.getUserListByCourse(courseId);}
//-------------------------------------------------------------------

        //    [[유저전체의 출석+프로필정보(유저번호,이름,프로필,출석정보)들 가져오기
//     ✅✅하고 +로 각출석상태 총합까지 만들어서 반환하기]]
        @GetMapping("/{courseId}/total")  //total

        public List<AttendanceProfileSum> attendanceList(@PathVariable Long courseId) {
                List<UserResponse> userList = attendanceService.getUserListByCourse(courseId);

                List<AttendanceProfileSum> profileSumList = new ArrayList<>();
                for (UserResponse userResponse : userList) {
                        AttendanceProfileSum profileSum = new AttendanceProfileSum();
                        profileSum.setUserResponse(userResponse);

                        // Get the current month
                        int currentMonth = LocalDate.now().getMonthValue();

                        List<AttendanceResponse> attendanceList = attendanceService.getAttendanceListByUserIdAndMonth(userResponse.getId(), currentMonth);
                        profileSum.setAttendanceResponse(attendanceList);

                        AttendanceSum attendanceSum = attendanceService.calculateAttendanceSum(userResponse.getId());
                        profileSum.setAttendanceSum(attendanceSum);

                        Map<Integer, Long> statusCountMap = attendanceList.stream()
                                .collect(Collectors.groupingBy(AttendanceResponse::getStatus, Collectors.counting()));

                        profileSum.setStatusCountMap(statusCountMap);

                        profileSumList.add(profileSum);
                }

                return profileSumList;
        }
//✅✅
        @GetMapping("/{id}/totalById")
        public List<AttendanceProfileSum> TotalById(@PathVariable("id") Long id) {
                List<UserResponse> userList = attendanceService.getUserListById(id);

                List<AttendanceProfileSum> profileSumList = new ArrayList<>();
                for (UserResponse userResponse : userList) {
                        AttendanceProfileSum profileSum = new AttendanceProfileSum();
                        profileSum.setUserResponse(userResponse);

                        // Get the current month
                        int currentMonth = LocalDate.now().getMonthValue();

                        List<AttendanceResponse> attendanceList = attendanceService.getAttendanceListByUserIdAndMonth(userResponse.getId(), currentMonth);
                        profileSum.setAttendanceResponse(attendanceList);

                        AttendanceSum attendanceSum = attendanceService.calculateAttendanceSum(userResponse.getId());
                        profileSum.setAttendanceSum(attendanceSum);

                        Map<Integer, Long> statusCountMap = attendanceList.stream()
                                .collect(Collectors.groupingBy(AttendanceResponse::getStatus, Collectors.counting()));

                        profileSum.setStatusCountMap(statusCountMap);

                        profileSumList.add(profileSum);
                }

                return profileSumList;
        }
//                                          [[인쇄버튼 만들기?????]]
//    @GetMapping("/print")
//    public List<Attendance> print() {
//        return attendanceService.print();
//    }

///=============================================================================

        //✅✅
        @GetMapping("/{id}/getAppliancesById")
        public List<ApplianceResponse> getAppliancesById(@PathVariable("id") Long id) {
                //return AttendanceRepository.findById(id).orElse(null);
                //return (List<ApplianceResponse>) attendanceService.getAppliancesById(id);
                return attendanceService.getAppliancesById(id);
        }

///=============================================================================
        // [[교육생의 신청허가에따른 status반영]]
        //  [[승인버튼 누르면 일어나는 허가 과정]]
        //바로위 getAppliancesById 의 공가신청 목록중에서 선택한 항목의 데이터를 요청에 담아서

        //✅✅승인버튼누를시\\
        @PutMapping("/AttendanceChangeYesRequest")
        public void AttendanceChangeYesRequest(
                @AuthenticationPrincipal Admin admin, @RequestBody AttendanceChangeYesRequest attendanceChangeYesRequest  ) {
                //+ Attendance의 status의 값을 Appliance의status으로 변경
                attendanceService.AttendanceAllowChangeYes
                        (attendanceChangeYesRequest.getAttendanceId(),//목록의 appliance안의 attendancId랑
                                attendanceChangeYesRequest.getStatus());// 변경하고픈상태값

                //해당 Appliance의 statusChangeAllow 값을 0->1로
                //ㄴ>외래키두게인 유저아이디랑 출석아이디로 공가신청칼럼찾고 그걸 고정값 변경
                attendanceService.ApplianceAllowChangeYse(attendanceChangeYesRequest.getAttendanceId(),admin.getId());
        }

        //✅✅미승인버튼누를시\\
        @PutMapping("/AttendanceChangeNoRequest")
        public void AttendanceChangeNoRequest(
                @AuthenticationPrincipal Admin admin, @RequestBody AttendanceChangeNoRequest attendanceChangeNoRequest  ) {
                //해당 Appliance의 statusChangeAllow 값을 0->1로
                attendanceService.AttendanceChangeNoRequest
                        (attendanceChangeNoRequest.getAttendanceId(),admin.getId());
        }
///=============================================================================
        //    @GetMapping("/{id}/appliance")
//    public ApplianceResponse viewApplianceForAttendance(@PathVariable("id") @Valid Long id) {
//        //유저id, applianceRequest->(reason,applianceDate,status)//유저8번으로 테스트
//        //!!!!!!!!!!!근데 이거 요청을"applianceDate": "2023-01-01",형식으로 보내져야한다!!!!!!!!!!!
//        // 출석정보 테이블의 id를 찾기 위해 ( 교육생id/공가신청의 날짜) 를 통해서 해당출결id를 찾음
//        Long attendanceId = attendanceService.getAttendanceId(id);
//        // 공가신청내용의DTO, url의 교육생 id, 찾은 출석 테이블 id를 Appliance에 저장 한다
//        attendanceService.createApplianceForAttendance(id);
////    }
//        @PostMapping("/appliance")
//        public void createApplianceForAttendance(@AuthenticationPrincipal Admin admin, @RequestBody ApplianceRequest applianceRequest) {
//                //유저id, applianceRequest->(reason,applianceDate,status)//유저8번으로 테스트
//                //!!!!!!!!!!!근데 이거 요청을"applianceDate": "2023-01-01",형식으로 보내져야한다!!!!!!!!!!!
//                // 출석정보 테이블의 id를 찾기 위해 ( 교육생id/공가신청의 날짜) 를 통해서 해당출결id를 찾음
//                Long attendanceId = attendanceService.getAttendanceId(admin.getId(), applianceRequest.getApplianceDate());
//
//
//                // 공가신청내용의DTO, url의 교육생 id, 찾은 출석 테이블 id를 Appliance에 저장 한다
//                attendanceService.createApplianceForAttendance(admin.getId(), applianceRequest, attendanceId);
//
//        }
///=============================================================================
        //            [[유저한명의 정보들 중 '일정페이지'
        //            (프로필,공가신청내역(날짜,원하는상태등등))들 가져오기]]
        //===========????   승인,대기중,미승인 상태표시 어캐  ????==========
        //-> id.name,imageUrl,attendances
//    @GetMapping("/{id}/getAppliancesById")
//    public List<ApplianceResponse> getAppliancesById(@PathVariable("id") Long id) {
//        //return AttendanceRepository.findById(id).orElse(null);
//         //return (List<ApplianceResponse>) attendanceService.getAppliancesById(id);
//        return attendanceService.getAppliancesById(id);
//    }
///=============================================================================
        //✅✅
        //            [[유저한명의 정보들 중 '정보'
        //            (...)들 가져오기]]
        //-> id.name,imageUrl,attendances//
        //코스정보추가
        //--유저 네임이랑 코스만 만들어주는 api만들자
        //코스아이디 는 보네줌
        @GetMapping("/{id}/userInfo")
        public UserResponse getUserInfo(@PathVariable("id") Long id) {
                //return AttendanceRepository.findById(id).orElse(null);
                // => 리퍼지터리와 컨트롤러가 만날일 없이 서비스가 조회 요청을 처리하도록 수정
                return attendanceService.getUserInfo(id);
        }
//=============================================================================
        //✅✅
        @GetMapping("/{id}/attendance")
        public List<AttendanceResponse> getAttendanceById(@PathVariable("id") Long id) {
                return attendanceService.getAttendanceById(id);
        }


}
