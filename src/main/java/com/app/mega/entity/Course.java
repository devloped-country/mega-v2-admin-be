package com.app.mega.entity;

import jakarta.persistence.*;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table
@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
@ToString(exclude = {"curriculumList", "notices", "institution", "users"})
public class Course {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  private String name;

  @OneToMany(mappedBy = "course")
  private List<Curriculum> curriculumList;

  @OneToMany(mappedBy = "course")
  private List<Notice> notices;

  @ManyToOne
  @JoinColumn(name = "institution_id")
  private Institution institution;

  @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
  private List<User> users;
}
