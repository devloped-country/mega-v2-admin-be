package com.app.mega.repository;


import com.app.mega.entity.Admin;
import com.app.mega.entity.NoteReceive;
import com.app.mega.entity.NoteSend;
import com.app.mega.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteReceiveRepository  extends JpaRepository<NoteReceive, Long> {
    List<NoteReceive> findAllByIsDeletedAndIsRealDeletedAndAdmin(Boolean isDeleted, Boolean isRealDeleted, Admin admin);
    List<NoteReceive> findAllByIsDeletedAndAdmin(Boolean isDeleted, Admin admin);
    List<NoteReceive> findAllByNoteSend(NoteSend noteSend);
    List<NoteReceive> findAllByIsRealDeleted(Boolean isRealDeleted);
    NoteReceive findByAdminAndNoteSend(Admin admin, NoteSend noteSend);
}