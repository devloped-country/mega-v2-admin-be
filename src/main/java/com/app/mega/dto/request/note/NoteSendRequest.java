package com.app.mega.dto.request.note;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class NoteSendRequest {

//    {
//        action: "sendToStudent",
//                type: "note",
//            from: myId,
//            to: to,
//            title: "",
//            content: "",
//    }
    private List<Long> to;
    private String title;
    private String content;

}
