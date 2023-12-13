package com.app.mega.common;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse {
    private String errorType;
    private String msg;
}