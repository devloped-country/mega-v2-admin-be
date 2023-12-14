package com.app.mega.dto.request;

import com.app.mega.entity.Course;
import com.app.mega.entity.Notice;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class NoticeRequest {
  private Long id;
  private Long courseId;
  @NotBlank(message = "제목은 필수 입력 값입니다.")
  private String title;
  @NotBlank(message = "내용은 필수 입력 값입니다.")
  private String content;
  private String textContent;
  private String author;
//  @NotBlank(message = "태그는 필수 입력 값입니다.")
  private List<String> tags;
  private LocalDateTime createdTime;
  private String thumbnail;

  public Notice toEntity(Course course) {
    return Notice.builder()
        .id(id)
        .course(course)
        .title(title)
        .content(content)
        .textContent(textContent)
        .author(author)
        .createdTime(LocalDateTime.now())
        .thumbnail(thumbnail)
        .build();
  }
}
