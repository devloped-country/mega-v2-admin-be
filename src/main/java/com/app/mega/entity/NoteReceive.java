package com.app.mega.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Data
@ToString(exclude = {"admin", "noteSend", "user"})
public class NoteReceive {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;//수신쪽지ID

  @NotNull
  @ColumnDefault("0")
  @Column(columnDefinition = "TINYINT(1)")
  private Boolean isRead; //쪽지읽음

  @NotNull
  @ColumnDefault("0")
  @Column(columnDefinition = "TINYINT(1)")
  private Boolean isDeleted; //쪽지삭제

  @NotNull
  @ColumnDefault("0")
  @Column(columnDefinition = "TINYINT(1)")
  private Boolean isRealDeleted; //쪽지완전삭제

  @ManyToOne
  private Admin admin;

  @NotNull
  @ManyToOne
  @JoinColumn(name = "note_id")
  private NoteSend noteSend;

  @ManyToOne
  private User user;
}
