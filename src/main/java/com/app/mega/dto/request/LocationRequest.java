package com.app.mega.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationRequest {
  private String email;
  private Float latitude;
  private Float longitude;
}
