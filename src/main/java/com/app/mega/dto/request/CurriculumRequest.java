package com.app.mega.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CurriculumRequest {
    private Long courseId;
    private Long id;
    private String subject;
    private Integer time;
    private String startDate;
    private String endDate;
    private List<String> contents;
}
