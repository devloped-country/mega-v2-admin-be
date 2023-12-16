package com.app.mega.controller;

import com.app.mega.common.CommonResponse;
import com.app.mega.common.handler.CustomValidationApiException;
import com.app.mega.dto.request.NoticeRequest;
import com.app.mega.dto.response.NoticeResponse;
import com.app.mega.service.jpa.NoticeService;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

@RestController
//@CrossOrigin(origins = "*")
@RequestMapping("/api/notice")
public class NoticeController {

  private final NoticeService noticeService;

  public NoticeController(NoticeService noticeService) {
    this.noticeService = noticeService;
  }

  @GetMapping("/notices/{id}")
  public ResponseEntity<CommonResponse<Page<NoticeResponse>>> readNotices(@PathVariable("id") Long courseId, Pageable pageable) {
    Page<NoticeResponse> noticeResponse = null;

    try {
      noticeResponse = noticeService.readNotices(pageable, courseId);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.OK).body(
          CommonResponse.<Page<NoticeResponse>>builder().responseCode(-1).responseMessage("에러")
              .data(null).build());
    }

    return ResponseEntity.status(HttpStatus.OK).body(
        CommonResponse.<Page<NoticeResponse>>builder().responseCode(1).responseMessage("성공")
            .data(noticeResponse).build());
  }

  @GetMapping("/{id}")
  public ResponseEntity<CommonResponse<NoticeResponse>> readNotice(@PathVariable("id") Long id) {
    NoticeResponse noticeResponse = null;

    try {
      noticeResponse = noticeService.readNotice(id);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.OK).body(
          CommonResponse.<NoticeResponse>builder().responseCode(-1).responseMessage("에러").data(null)
              .build());
    }

    return ResponseEntity.status(HttpStatus.OK).body(
        CommonResponse.<NoticeResponse>builder().responseCode(1).responseMessage("성공")
            .data(noticeResponse).build());
  }

  @PostMapping("/{id}")
  public CommonResponse createNotice(@Valid @RequestBody NoticeRequest noticeRequest,
      BindingResult bindingResult, @PathVariable("id") Long courseId) {
    if (bindingResult.hasErrors()) {
      Map<String, String> errorMap = new HashMap<>();
      for (FieldError error : bindingResult.getFieldErrors()) {
        errorMap.put(error.getField(), error.getDefaultMessage());
      }
      throw new CustomValidationApiException("유효성검사 실패함", errorMap);
    } else {
      noticeService.createNotice(noticeRequest, courseId);
      return new CommonResponse<>(1, "공지글 등록 성공");
    }
  }

  @PutMapping("/{id}")
  public void updateNotice(@RequestBody NoticeRequest noticeRequest, @PathVariable("id") Long id) {
    noticeService.updateNotice(noticeRequest, id);
  }

  @DeleteMapping("/{id}")
  public void deleteNotice(@PathVariable("id") Long id) {
    noticeService.deleteNotice(id);
  }
}
