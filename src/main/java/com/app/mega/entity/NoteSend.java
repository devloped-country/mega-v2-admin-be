package com.app.mega.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString(exclude = {"admin", "noteReceives", "user"})
public class NoteSend {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id; //발신쪽지 ID

  @NotNull
  private String title; //쪽지제목

  @NotNull
  @Lob
  private String content; //쪽지내용

  @NotNull
  private LocalDateTime createTime; //쪽지생성일지

  @NotNull
  @ColumnDefault("0")
  @Column(columnDefinition = "TINYINT(1)")
  private Boolean isRealDeleted; //쪽지완전삭제

  @ManyToOne
  private Admin admin;

  @OneToMany(mappedBy = "noteSend", fetch = FetchType.EAGER)
  private List<NoteReceive> noteReceives;

  @ManyToOne
  private User user;
}
