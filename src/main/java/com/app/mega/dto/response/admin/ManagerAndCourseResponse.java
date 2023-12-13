package com.app.mega.dto.response.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ManagerAndCourseResponse {
  private Map<String,String> managerInfo;
  private Map<Long,String> courseInfo;
//    {
//        "managerInfo": {
//        "송정희": "sjh8924@naver.com"
//    },
//        "courseInfo": [
//        "개발자 양성과정"
//    ]
//    }
}