package com.app.mega.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.apache.ibatis.annotations.One;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table
@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
@ToString(exclude = {"courseList", "admin", "user"})
public class Institution {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  private String name;

  @Column(columnDefinition = "FLOAT(7,5)")
  @ColumnDefault("0")
  private Float latitude;

  @Column(columnDefinition = "FLOAT(9,6)")
  @ColumnDefault("0")
  private Float longitude;

  @NotNull
  private String address;

  @UpdateTimestamp
  private LocalDate subscriptionStartDate;

  @ColumnDefault("0")
  @Column(columnDefinition = "TINYINT(1)")
  private boolean subscriptionStatus;

  @OneToMany(mappedBy = "institution", fetch = FetchType.EAGER)
  private List<Course> courseList;

  @OneToMany(mappedBy = "institution", fetch = FetchType.EAGER)
  private List<Admin> admin;

  @OneToMany(mappedBy = "institution", fetch = FetchType.EAGER)
  private List<User> user;
//추가추가
  @OneToMany(mappedBy = "institution", fetch = FetchType.EAGER)
  @JsonManagedReference
  private List<Payment> payment;
}
