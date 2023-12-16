package com.app.mega.controller;

import com.app.mega.common.CommonResponse;
import com.app.mega.common.handler.CustomValidationApiException;
import com.app.mega.dto.request.InstitutionRequest;
import com.app.mega.dto.request.LocationRequest;
import com.app.mega.dto.response.LocationResponse;
import com.app.mega.dto.response.NoticeResponse;
import com.app.mega.entity.Admin;
import com.app.mega.entity.User;
import com.app.mega.service.mybatis.InstitutionService;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/institution")
public class InstitutionController {

  private final InstitutionService institutionService;

  public InstitutionController(InstitutionService institutionService) {
    this.institutionService = institutionService;
  }

  @GetMapping
  public ResponseEntity<CommonResponse<LocationResponse>> readInstitutionLocation(@AuthenticationPrincipal Admin admin) {
    String email = admin.getEmail();
    LocationResponse institution = institutionService.findInstitutionLocationByEmail(email);

    return ResponseEntity.status(HttpStatus.OK).body(
        CommonResponse.<LocationResponse>builder().responseCode(1).responseMessage("성공")
            .data(institution).build());
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

  @PutMapping
  public void updateInstitutionLocation(@AuthenticationPrincipal Admin admin, @RequestBody LocationRequest locationRequest) {
      String email = admin.getEmail();
      institutionService.updateInstitutionLocationByEmail(email, locationRequest);
  }
}
