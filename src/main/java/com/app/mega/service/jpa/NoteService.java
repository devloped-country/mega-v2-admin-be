package com.app.mega.service.jpa;


import com.app.mega.dto.request.note.NoteSendRequest;
import com.app.mega.dto.response.NoteResponse;
import com.app.mega.dto.response.note.*;
import com.app.mega.entity.Admin;
import com.app.mega.entity.NoteReceive;
import com.app.mega.entity.NoteSend;
import com.app.mega.entity.User;
import com.app.mega.repository.CourseRepository;
import com.app.mega.repository.NoteReceiveRepository;
import com.app.mega.repository.NoteSendRepository;
import com.app.mega.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NoteService {
    private final NoteSendRepository noteSendRepository;
    private final NoteReceiveRepository noteReceiveRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    //과정에 해당하는 학생 불러오기
    public List<ReceiverResponse> readReceiver (Long courseId) {
        //List<User> receivers = courseRepository.findById(courseId).get().getUsers();
        List<User> receivers = userRepository.findAllByCourse(courseRepository.findById(courseId).get());
        List<ReceiverResponse> receiversInfo = new ArrayList<>();
        for(User receiver:receivers) {
            ReceiverResponse receiverResponse = ReceiverResponse.builder()
                    .id(receiver.getId())
                    .name(receiver.getName())
                    .email(receiver.getEmail())
                    .build();
            receiversInfo.add(receiverResponse);
        }
        return receiversInfo;
    }

    //쪽지 저장 (발신)
    public AfterNoteSendResponse registerNote(NoteSendRequest request, Admin admin) {
        System.out.println("registerNote");

        String title = request.getTitle();
        String content = request.getContent();

        List<User> to = new ArrayList<>();
        for(Long receiverId : request.getTo()) {
            User user = userRepository.findById(receiverId).get();
            to.add(user);
        }

        //발신쪽지 데이터 저장
        NoteSend noteSend = NoteSend.builder()
                .title(title)
                .content(content)
                .createTime(LocalDateTime.now())
                .isRealDeleted(false)
                .admin(admin)
                .build();
        noteSendRepository.save(noteSend);

        //수신쪽지 데이터 저장
        for(User receiver : to) {
            NoteReceive noteReceive = NoteReceive.builder()
                    .noteSend(noteSend)
                    .isRealDeleted(false)
                    .isDeleted(false)
                    .isRead(false)
                    .user(receiver)
                    .build();
            noteReceiveRepository.save(noteReceive);
        }

        return AfterNoteSendResponse.builder().myName(admin.getName()).noteSendId(noteSend.getId()).build();
    }

    //발신쪽지 불러오기
    public List<SendedNoteResponse> readNoteSend(Admin admin) {
        List<NoteSend> sentNotes = noteSendRepository.findAllByIsRealDeletedAndAdmin(false, admin);
        List<SendedNoteResponse> notesInfo = new ArrayList<>();
        for(NoteSend sentNote: sentNotes) {
            List<NoteReceive> noteTos = sentNote.getNoteReceives();
            //List<NoteReceive> noteTos = noteReceiveRepository.findAllByNoteSend(sentNote);
            List<String> noteToNames = new ArrayList<>();
            for (NoteReceive noteTo : noteTos) {
                noteToNames.add(noteTo.getUser().getName());
            }
            SendedNoteResponse sentNoteResponse = SendedNoteResponse.builder()
                    .id(sentNote.getId())
                    .title(sentNote.getTitle())
                    .content(sentNote.getContent())
                    .to(noteToNames)
                    .time(sentNote.getCreateTime())
                    .build();
            notesInfo.add(sentNoteResponse);
        }
        return notesInfo;
    }

    //수신쪽지 불러오기
    public List<ReceivedNoteResponse> readNoteReceive(Admin admin) {
        System.out.println("NoteService.readNoteReceive");
        List<NoteReceive> receivedNotes = noteReceiveRepository.findAllByIsDeletedAndAdmin(false, admin);  //수신인 정보
        List<ReceivedNoteResponse> notesInfo = new ArrayList<>();
        for(NoteReceive receivedNote:receivedNotes) {
            NoteSend note = receivedNote.getNoteSend();
            ReceivedNoteResponse receivedNoteResponse = ReceivedNoteResponse.builder()
                    .id(receivedNote.getNoteSend().getId())
                    .title(note.getTitle())
                    .content(note.getContent())
                    .isRead(receivedNote.getIsRead())
                    .senderName(note.getUser().getName())
                    .time(note.getCreateTime())
                    .build();
            notesInfo.add(receivedNoteResponse);
        }
        return notesInfo;
    }

    //휴지통 불러오기 (수신)
    public List<TrashNoteResponse> readTrashNote(Admin admin) {
        List<NoteReceive> trashNotes = noteReceiveRepository.findAllByIsDeletedAndIsRealDeletedAndAdmin(true, false, admin);
        List<TrashNoteResponse> notesInfo = new ArrayList<>();
        for(NoteReceive trashNote : trashNotes) {
            NoteSend note = trashNote.getNoteSend();
            TrashNoteResponse trashNoteResponse = TrashNoteResponse.builder()
                .id(trashNote.getId())
                .title(note.getTitle())
                .content(note.getContent())
                .from(note.getUser().getName())
                .build();
            notesInfo.add(trashNoteResponse);
        }
        return notesInfo;
    }

    //수신쪽지 삭제 (휴지통 넣기)
    public List<ReceivedNoteResponse> deleteReceivedNotes (List<Long> noteIdsForDelete, Admin admin) {
        for(Long noteId : noteIdsForDelete) {
            NoteReceive noteReceive = noteReceiveRepository.findById(noteId).get();
            noteReceive.setIsDeleted(true);
            noteReceiveRepository.save(noteReceive);
        }
        return readNoteReceive(admin);
    }

    //수신쪽지 완전삭제
    public List<TrashNoteResponse> realDeleteReceivedNotes (List<Long> noteIdsForDelete, Admin admin) {
        for(Long noteId : noteIdsForDelete) {
            NoteReceive noteReceive = noteReceiveRepository.findById(noteId).get();
            noteReceive.setIsRealDeleted(true);
            noteReceiveRepository.save(noteReceive);
        }
         return readTrashNote(admin);
    }

    //발신쪽지 완전삭제
    public List<SendedNoteResponse> realDeleteSendedNotes (List<Long> noteIdsForDelete, Admin admin) {
        for(Long noteId : noteIdsForDelete) {
            NoteSend noteSend = noteSendRepository.findById(noteId).get();
            noteSend.setIsRealDeleted(true);
            noteSendRepository.save(noteSend);
        }
        return readNoteSend(admin);
    }

    public NoteResponse readNote(Long id, Admin admin) {
        NoteSend noteSend = noteSendRepository.findById(id).get();
        NoteReceive noteReceive = noteReceiveRepository.findByAdminAndNoteSend(admin, noteSend);
        noteReceive.setIsRead(true);
        noteReceiveRepository.save(noteReceive);
        return NoteResponse.builder()
                .content(noteSend.getContent())
                .from(noteSend.getUser().getName())
                .to(admin.getName())
                .time(String.valueOf(noteSend.getCreateTime()))
                .build();
    }
}
