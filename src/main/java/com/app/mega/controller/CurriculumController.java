package com.app.mega.controller;

import com.app.mega.common.CommonResponse;
import com.app.mega.dto.request.CurriculumRequest;
import com.app.mega.dto.response.CurriculumResponse;
import com.app.mega.service.mybatis.CurriculumService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/curriculum")
@CrossOrigin(origins = "*")
public class CurriculumController {
    private final CurriculumService curriculumService;

    //커리큘럼 정보 Create
    @PostMapping("/register")
    public ResponseEntity registerCurriculum (@RequestBody CurriculumRequest request) throws Exception {
        return ResponseEntity.ok(curriculumService.registerCurriculum(request));

/*    {
        "curriculum": {
            "subject이름": {
                    "time": "35",
                    "startDate": "07/20",
                    "endDate": "07/20",
                    "detailSubjects": ["과정1", "과정2", "과정3"]
             },
            "subject이름": {
                    "time": "35",
                    "startDate": "07/20",
                    "endDate": "07/20",
                    "detailSubjects": ["과정1", "과정2", "과정3"]
             },
            "subject이름": {
                    "time": "35",
                    "startDate": "07/20",
                    "endDate": "07/20",
                    "detailSubjects": ["과정1", "과정2", "과정3"]
             }
        }
    }*/

    }

    //커리큘럼 정보 Read ｂｙ Ｃｏｕｒｓｅ
    @GetMapping("/read/{id}")
    public ResponseEntity<CommonResponse<List<CurriculumResponse>>> readCurriculumList(@PathVariable("id") Long course) throws Exception {
        List<CurriculumResponse> courses;
        try {
            courses = curriculumService.readCurriculumList(course);
            System.out.println(courses);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.OK).body(
                    CommonResponse.<List<CurriculumResponse>>builder().responseCode(-1).responseMessage("실패")
                            .data(null).build());
        }

        return ResponseEntity.status(HttpStatus.OK).body(
                CommonResponse.<List<CurriculumResponse>>builder().responseCode(1).responseMessage("성공")
                        .data(courses).build());
    }

    //커리큘럼 정보 Read by CurriculumId
    @GetMapping("read/{id}/{curriculumId}")
    public CurriculumResponse readCurriculum(@PathVariable("id") Long course, @PathVariable("curriculumId") Long curriculum) {
        CurriculumResponse curriculumList;
        curriculumList = curriculumService.readCurriculum(course, curriculum);

        return curriculumList;
    }



    //커리큘럼 정보 Update
    @PutMapping("/update")
    public void editCurriculum(@RequestBody CurriculumRequest request) throws Exception {
        System.out.println(request);
        curriculumService.editCurriculum(request);

    }

    //커리큘럼 정보 Delete
    @DeleteMapping("/delete/{id}")
    public void deleteCurriculum(@PathVariable("id") Long id) throws Exception {
        curriculumService.deleteCurriculum(id);
    }

    //커리큘럼 목록 순서 Update
    @PutMapping("/order")
    public void editCurriculumList(@RequestBody CurriculumRequest request) throws Exception {
        curriculumService.editCurriculumList(request);
    }
}
