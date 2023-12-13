package com.app.mega.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@NoArgsConstructor
public class ApplianceId implements Serializable {
  private Long attendanceId;
  private Long userId;
}
