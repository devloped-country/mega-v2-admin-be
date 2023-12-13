package com.app.mega.dto.response.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

// 토큰 및 isManager 반환 DTO
public class AuthenticationResponse {
    private String token;
    private Boolean isManager;
    private Long id;
}