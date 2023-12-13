package com.app.mega.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DetailSubjectResponse {
    private Long curriculum_id;

    private Long id;
    private String content;
}
