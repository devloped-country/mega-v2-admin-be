package com.app.mega.dto.request.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder

// 매니저 회원가입 요청 DTO
public class AdminSaveRequest {
    private String name;
    private String email;
    private String password;
    private String phone;
}