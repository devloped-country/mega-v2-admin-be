package com.app.mega.common.handler;

import com.app.mega.common.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(CustomValidationApiException.class)
    public ResponseEntity<CommonResponse<Map<String, String>>> validationApiException(CustomValidationApiException e) {
        CommonResponse cmResponseDTO = new CommonResponse<> (-1, e.getMessage(), e.getErrorMap());
        ResponseEntity responseEntity = new ResponseEntity<>(cmResponseDTO, HttpStatus.BAD_REQUEST);
        System.out.println(responseEntity);
        return responseEntity;

        /* 응답값
        {
            "code": -1,
            "errorMessage": "유효성 검사 실패함",
            "errorMap": {
                "name": "공백일 수 없습니다",
                "email": "공백일 수 없습니다",
                "username": "크기가 1에서 20 사이여야 합니다"
            }
        }
         */
    }
}
