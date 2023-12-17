package com.app.mega.repository;


import com.app.mega.entity.Admin;
import com.app.mega.entity.NoteSend;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteSendRepository  extends JpaRepository<NoteSend, Long> {
    List<NoteSend> findAllByIsRealDeletedAndAdmin(Boolean isRealDeleted, Admin admin);

}