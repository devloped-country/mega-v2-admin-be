package com.app.mega.repository;

import com.app.mega.entity.NoticeTag;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeTagRepository extends JpaRepository<NoticeTag, Long> {
  List<NoticeTag> findByNoticeId(Long id);
}
