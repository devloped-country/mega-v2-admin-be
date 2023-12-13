package com.app.mega.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table
@NoArgsConstructor
@Data
@ToString(exclude = "admin")
public class AdminNotification {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;//열람 아이디

  @NotNull
  private String type; //알림타입

  @NotNull
  private Long sourceId;//알림소스아이디

  @NotNull
  private LocalDateTime creatTime; //알림생성일지

  @NotNull
  @Column(columnDefinition = "TINYINT(1)")
  private Boolean isRead;//알림읽음

  @ManyToOne
  @JoinColumn(name = "admin_id")
  private Admin admin;//관리자 아이디
}
