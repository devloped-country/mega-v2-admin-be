package com.app.mega.dto.request;

import lombok.Data;
import lombok.ToString;

@ToString
@Data

public class AttendanceChangeYesRequest {
//    private Long id;//이건 출석id번호가 아닌 교육생id번호이다
    private Long attendanceId;//appliance에 저장된 출석데이터의 번호
    private Integer status; //변경하고싶은 신청appliance의 status값
//    private Long statusChangeAllow; //신청내역에의 0,1,2에 따라 대기,승인,미승인 으로 변경할수있게
    //이건 그냥 바로 쿼리문으로 바꾸자

    public AttendanceChangeYesRequest(Long attendanceId, Integer status,  Long statusChangeAllow) {
//        this.id = id;
        this.attendanceId = attendanceId;
        this.status = status;
//        this.statusChangeAllow=statusChangeAllow;
    }
}
