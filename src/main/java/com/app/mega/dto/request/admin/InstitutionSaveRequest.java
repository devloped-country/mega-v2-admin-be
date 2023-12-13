package com.app.mega.dto.request.admin;

import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class InstitutionSaveRequest {
    private Float latitude;
    private Float longitude;
    private String address;
    private String name;  // 관리자 name에도 name을 기관명으로 저장할 것.
    private String email;
    private String password;
    private String phone;
    private List<String> courses;
}
