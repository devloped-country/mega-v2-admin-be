package com.app.mega.dto.response;

import com.app.mega.dto.request.PageRequest;
import com.app.mega.entity.Notice;
import java.util.List;
import lombok.Data;

@Data
public class PageResponse {
  private List<Notice> noticeList;
  private List<Integer> pageNumList;
  private PageRequest pageRequest;
  private boolean prev, next;
  private int totalCount, prevPage, nextPage, totalPage, current;

  public PageResponse(List<Notice> noticeList, PageRequest pageRequest, long totalCount) {
    this.noticeList = noticeList;
    this.pageRequest = pageRequest;
    this.totalCount = (int) totalCount;

    int end = (int)(Math.ceil(pageRequest.getPage() / 10.0)) * 10;

    int start = end - 9;
  }
}
