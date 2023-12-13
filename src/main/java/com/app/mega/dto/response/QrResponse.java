package com.app.mega.dto.response;

import com.app.mega.entity.Qr;
import lombok.*;

@Data
public class QrResponse {
  private Long id;
  private String qr;

  public QrResponse(Qr qr) {
    this.id = qr.getInstitutionId();
    this.qr = qr.getQr();
  }
}

