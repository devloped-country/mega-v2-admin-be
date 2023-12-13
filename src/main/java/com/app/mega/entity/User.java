package com.app.mega.entity;

import jakarta.persistence.*;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Builder
@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString(exclude = {"institution", "noteSends", "noteReceives", "userNotifications", "attendances", "appliances"})
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  private String name;

  @NotNull
  private String email;

  @NotNull
  private String password;

  @NotNull
  @Column(length = 30)
  private String phone;

  @NotNull
  @ColumnDefault("0")
  @Column(columnDefinition = "TINYINT(1)")
  private Boolean isIdentified;

  @ManyToOne
  @JoinColumn(name = "institution_id")
  private Institution institution;

  @OneToMany(mappedBy = "user")
  private List<UserNotification> userNotifications;

//  @OneToMany(mappedBy = "user")
//  private List<Attendance> attendances;

  @ManyToOne
  @JoinColumn(name = "course_id")
  private Course course;
}
