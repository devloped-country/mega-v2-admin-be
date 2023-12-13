package com.app.mega.dto.request;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstitutionRequest {
  private Long id;
  private String name;
  private Float latitude;
  private Float longitude;
}
