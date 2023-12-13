package com.app.mega.dto.request;

import com.app.mega.entity.Notice;
import com.app.mega.entity.NoticeTag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class NoticeTagsRequest {
  private Long id;
  private String tag;
  private Notice notice;

  public NoticeTag toEntity(Notice notice, String tag) {
    return NoticeTag.builder()
        .tag(tag)
        .notice(notice)
        .build();
  }
}