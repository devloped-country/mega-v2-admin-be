package com.app.mega.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CurriculumResponse {
    private Long course_id;
//    private Long institution_id;
//    private String name;

    private Long curriculum_id;
    private String subject;
    private Integer time;
    private String startDate;
    private String endDate;
    private Integer listOrder;

    private List<DetailSubjectResponse> content = new ArrayList<>();

    //Map<커리큘럼한개 당, 상세교과 여러개!>
//    Map<String, Map<String, Object>> curriculum;

//    {
//        "curriculum": {
//            "subject이름": {
//                    "time": "35",
//                    "startDate": "07/20",
//                    "endDate": "07/20",
//                    "detailSubjects": ["과정1", "과정2", "과정3"]
//             },
//            "subject이름": {
//                    "time": "35",
//                    "startDate": "07/20",
//                    "endDate": "07/20",
//                    "detailSubjects": ["과정1", "과정2", "과정3"]
//             },
//            "subject이름": {
//                    "time": "35",
//                    "startDate": "07/20",
//                    "endDate": "07/20",
//                    "detailSubjects": ["과정1", "과정2", "과정3"]
//             }
//        }
//    }

}
