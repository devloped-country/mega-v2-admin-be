package com.app.mega.dto.response;

import com.app.mega.entity.*;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
@ToString
public class UserResponse {
        private Long id;
        private String name;
        private String email;
        private String phone;
        private Boolean isSigned;

        public User toEntity() {
            return User.builder()
                    .name(name)
                    .email(email)
                    // 비밀번호는 여기에서 복사하지 않음
                    .phone(phone)
                    .isIdentified(isSigned)
                    .build();
        }
    }