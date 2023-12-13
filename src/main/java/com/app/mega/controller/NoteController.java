package com.app.mega.controller;

import com.app.mega.dto.request.note.NoteIdRequest;
import com.app.mega.dto.request.note.NoteSendRequest;
import com.app.mega.dto.request.note.ReceiverRequest;
import com.app.mega.dto.response.note.ReceivedNoteResponse;
import com.app.mega.dto.response.note.ReceiverResponse;
import com.app.mega.dto.response.note.SendedNoteResponse;
import com.app.mega.dto.response.note.TrashNoteResponse;
import com.app.mega.entity.Admin;
import com.app.mega.entity.NoteSend;
import com.app.mega.entity.User;
import com.app.mega.service.jpa.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/note")
public class NoteController {
    private final NoteService noteService;

    //과정에 해당하는 학생들 불러오기 (쪽지쓰기 선택시)
    @GetMapping("/receivers")
    public List<ReceiverResponse> readReceiver (@RequestBody ReceiverRequest request) {
        return noteService.readReceiver(request.getCourseId());
    }

    //발신시 쪽지 저장
    @PostMapping("/register")
    public void registerNote (@RequestBody NoteSendRequest request, @AuthenticationPrincipal Admin admin) throws Exception {
        noteService.registerNote(request, admin);
    }

    //수신쪽지함 불러오기
    @GetMapping("/received")
    public List<ReceivedNoteResponse> readNoteReceive(@AuthenticationPrincipal Admin admin) {
        return noteService.readNoteReceive(admin);
    }

    //발신쪽지함 불러오기
    @GetMapping("/sent")
    public List<SendedNoteResponse> readNoteSend(@AuthenticationPrincipal Admin admin) {
        return noteService.readNoteSend(admin);
    }

    //휴지통 불러오기
    @GetMapping("/trash")
    public List<TrashNoteResponse> readTrashNote(@AuthenticationPrincipal Admin admin) {
        return noteService.readTrashNote(admin);
    }

    //수신쪽지 삭제 (휴지통 넣기)
    @PutMapping("/delete_received")
    public List<ReceivedNoteResponse> deleteReceivedNotes (@RequestBody NoteIdRequest request, @AuthenticationPrincipal Admin admin) {
        return noteService.deleteReceivedNotes(request.getSelectedNoteId(), admin);
    }

    //수신쪽지 완전삭제
    @PutMapping("/real_delete_received")
    public List<TrashNoteResponse> realDeleteReceivedNotes (@RequestBody NoteIdRequest request, @AuthenticationPrincipal Admin admin) {
        return noteService.realDeleteReceivedNotes(request.getSelectedNoteId(), admin);
    }


    //발신쪽지 완전삭제
    @PutMapping("/real_delete_sent")
    public List<SendedNoteResponse> realDeleteSendedNotes (@RequestBody NoteIdRequest request, @AuthenticationPrincipal Admin admin) {
        return noteService.realDeleteSendedNotes(request.getSelectedNoteId(), admin);
    }
}