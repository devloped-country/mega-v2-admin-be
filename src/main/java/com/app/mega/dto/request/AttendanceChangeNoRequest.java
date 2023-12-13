package com.app.mega.dto.request;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class AttendanceChangeNoRequest {
    private Long id;//이건 출석id번호가 아닌 교육생id번호이다
    private Long attendanceId;//appliance에 저장된 출석데이터의 번호
    //    private Integer status; //변경하고싶은 신청appliance의 status값
    private Long statusChangeAllow; //신청내역에의 0,1,2에 따라 대기,승인,미승인 으로 변경할수있게

    public AttendanceChangeNoRequest(Long id,Long attendanceId, Integer status,  Long statusChangeAllow) {
        this.id = id;
        this.attendanceId = attendanceId;
//        this.status = status;
        this.statusChangeAllow=statusChangeAllow;
    }
}