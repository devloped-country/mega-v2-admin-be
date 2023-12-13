package com.app.mega.controller;

import com.app.mega.common.CommonResponse;
import com.app.mega.dto.request.QrRequest;
import com.app.mega.dto.response.NoticeResponse;
import com.app.mega.dto.response.QrResponse;
import com.app.mega.service.jpa.AwsDynamoDbService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/qr")
@RequiredArgsConstructor
public class QrController {
  private final AwsDynamoDbService awsDynamoDbService;

  @PostMapping
  public QrResponse createQr(@RequestBody QrRequest qrRequest) {
      return awsDynamoDbService.createQr(qrRequest.getId());
  }

  @GetMapping("/{qr}")
  public ResponseEntity<CommonResponse<Boolean>> readQr(@PathVariable("qr") String qr) {
    if (awsDynamoDbService.readQr().equals(qr)) {
      return ResponseEntity.status(HttpStatus.OK).body(
          CommonResponse.<Boolean>builder().responseCode(1).responseMessage("성공")
              .data(true).build());
    }

    return ResponseEntity.status(HttpStatus.OK).body(
        CommonResponse.<Boolean>builder().responseCode(-1).responseMessage("에러")
            .data(false).build());
  }
}
