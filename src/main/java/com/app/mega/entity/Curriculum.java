package com.app.mega.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Entity
@Table
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Curriculum {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  private String subject; //교과목명

  @NotNull
  private Integer time; //시간

  @NotNull
  private String startDate; //시작날짜

  @NotNull
  private String endDate; //종료날짜

  @NotNull
  private Integer listOrder; //목록순서

  @OneToMany(mappedBy = "curriculum", fetch = FetchType.LAZY)
  private List<DetailSubject> detailSubjects;

  @ManyToOne
  @JoinColumn(name = "course_id")
  private Course course;
}
