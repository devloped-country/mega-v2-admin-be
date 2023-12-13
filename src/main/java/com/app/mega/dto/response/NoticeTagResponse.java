package com.app.mega.dto.response;

import com.app.mega.entity.NoticeTag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class NoticeTagResponse {
  private Long id;
  private String tag;

  public NoticeTagResponse(NoticeTag noticeTag) {
    this.id = noticeTag.getId();
    this.tag = noticeTag.getTag();
  }
}
