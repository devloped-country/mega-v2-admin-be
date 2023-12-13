package com.app.mega.entity;

import jakarta.persistence.*;
import java.util.Collection;
import java.util.List;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table
@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
@ToString(exclude = {"adminNotifications", "institution"})
public class Admin implements UserDetails {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id; //관리자 아이디

  @NotNull
  private String name; //이름

  @NotNull
  private String email; //이메일

  @NotNull
  private String password; //비밀번호

  @NotNull
  @Column(length = 30)
  private String phone; //휴대폰번호

  @NotNull
  @ColumnDefault("1")
  @Column(columnDefinition = "TINYINT(1)")
  private Boolean isManager; //기관0, 매니저1

  @OneToMany(mappedBy = "admin", fetch= FetchType.LAZY)
  private List<AdminNotification> adminNotifications;

  @ManyToOne
  @JoinColumn(name = "institution_id")
  private Institution institution;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
//    return List.of(new SimpleGrantedAuthority(role.name()));
  }

  @Override
  public String getUsername() {
    return this.email;
  }

  @Override
  public String getPassword() {
    return this.password;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}