package com.app.mega.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table
//@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString(exclude = {"user"})
public class Attendance {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private LocalDateTime startTime;

  private LocalDateTime endTime;

  @NotNull
  @ColumnDefault("0")
  private Integer status;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;


  @Column(nullable = true)
  private LocalDate attendanceDate;
  public Attendance(Long id, LocalDateTime startTime, LocalDateTime endTime, Integer status, User user,LocalDate attendanceDate)


  {
    this.id = id;
    this.startTime = startTime;
    this.endTime = endTime;
    this.status = status;
    this.user = user;
    this.attendanceDate=attendanceDate;
  }

  public Attendance(Long id, LocalDateTime startTime, LocalDateTime endTime, Integer status) {
    this.id=id;
    this.startTime=startTime;
    this.endTime=endTime;
    this.status=status;

  }

  public Attendance(Long id, Integer status) {
    this.id=id;
    this.status=status;

  }

//  public void patch(Attendance attendance) {
//  }



}
