package com.app.mega.dto.request.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

//로그인 요청 DTO
public class AuthenticationRequest {
    private String email;
    private String password;
}