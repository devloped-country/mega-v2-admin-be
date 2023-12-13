package com.app.mega.dto.request.user;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder

// 교육생 저장 DTO
public class UserSaveRequest {
    private String name;
    private String email;
    private String phone;
    private String course;
}
