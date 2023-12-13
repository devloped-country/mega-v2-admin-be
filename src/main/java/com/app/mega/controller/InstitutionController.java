package com.app.mega.controller;

import com.app.mega.common.CommonResponse;
import com.app.mega.common.handler.CustomValidationApiException;
import com.app.mega.dto.request.InstitutionRequest;
import com.app.mega.service.mybatis.InstitutionService;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/institution")
public class InstitutionController {

  private final InstitutionService institutionService;

  public InstitutionController(InstitutionService institutionService) {
    this.institutionService = institutionService;
  }

  @PostMapping
  public CommonResponse createInstitution(@Valid @RequestBody InstitutionRequest institutionRequest,
      BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      Map<String, String> errorMap = new HashMap<>();
      for (FieldError error : bindingResult.getFieldErrors()) {
        errorMap.put(error.getField(), error.getDefaultMessage());
      }
      throw new CustomValidationApiException("유효성 검사 실패", errorMap);
    } else {
      Long id = institutionService.saveInstitution(institutionRequest);
      return new CommonResponse<>(1, "기관 등록 성공", id);
    }
  }
}
