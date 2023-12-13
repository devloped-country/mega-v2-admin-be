package com.app.mega.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MessageRequest {
  private String qr;
  private String createdTime;
  private String user;
}
