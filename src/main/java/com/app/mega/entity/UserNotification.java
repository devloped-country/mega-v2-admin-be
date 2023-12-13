package com.app.mega.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import java.sql.Date;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.CurrentTimestamp;

@Entity
@Table
@NoArgsConstructor
@Data
@ToString(exclude = {"user"})
public class UserNotification {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id; //알림 아이디

  @NotNull
  private String type; //알림타입

  @NotNull
  private Long sourceId;

  @NotNull
  @CreationTimestamp
  private LocalDateTime createTime;

  @NotNull
  @ColumnDefault("0")
  @Column(columnDefinition = "TINYINT(1)")
  private Boolean isRead;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;
}
