package com.app.mega.service.mybatis;

import com.app.mega.common.CommonResponse;
import com.app.mega.dto.request.CurriculumRequest;
import com.app.mega.dto.response.CurriculumResponse;
import com.app.mega.dto.response.DetailSubjectResponse;
import com.app.mega.entity.Course;
import com.app.mega.entity.Curriculum;
import com.app.mega.entity.DetailSubject;
import com.app.mega.mapper.CurriculumMapper;
import com.app.mega.repository.CourseRepository;
import com.app.mega.repository.CurriculumRepository;
import com.app.mega.repository.DetailSubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CurriculumService {

    private final CurriculumRepository curriculumRepository;
    private final DetailSubjectRepository detailSubjectRepository;
    private final CourseRepository courseRepository;
    private final CurriculumMapper curriculumMapper;

    //커리큘럼 등록
    @Transactional
    public ResponseEntity registerCurriculum(CurriculumRequest request) throws Exception {
        Course course = courseRepository.findById(request.getCourseId()).orElseThrow(IllegalArgumentException::new);
        if (course == null) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    CommonResponse.builder()
                            .responseCode(0)
                            .responseMessage("[실패] 해당하는 코스를 찾을 수 없습니다.")
                            .data(null)
                            .build());
        }

        //교과 등록
        Curriculum curriculum = Curriculum.builder()
                .subject(request.getSubject())
                .time(request.getTime())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .course(course)
                .listOrder(curriculumRepository.prevCurriculumCount(request.getCourseId()))
                .build();
        curriculumRepository.save(curriculum);

        //상세교과 등록
        for(String content : request.getContents()) {
            DetailSubject detailSubject = DetailSubject.builder()
                    .content(content)
                    .curriculum(curriculum)
                    .build();
            detailSubjectRepository.save(detailSubject);
        }

        //List<CurriculumResponse> afterSave = readCurriculum(courseId);

        return ResponseEntity.status(HttpStatus.OK).body(
                CommonResponse.builder()
                        .responseCode(1)
                        .responseMessage("[성공]")
                        //.data(afterSave)
                        .build());
    }

    //커리큘럼 불러오기
    @Transactional(readOnly = true)
    public List<CurriculumResponse> readCurriculumList(Long course) throws Exception {
//        System.out.println(curriculumMapper.findAllCurriculum(course.getId()));

        //List<CurriculumResponse> curriculumList = curriculumMapper.findAllCurriculum(course.getId());
//        List<CurriculumResponse> curriculumList = new ArrayList<>();
//
//       for(CurriculumResponse curriculumResponse : curriculumList) {
//           List<CurriculumResponse> curriculumResponseList = curriculumMapper.findAllCurriculum(request.getId());
//           List<DetailSubjectResponse> detailSubjectResponses = new ArrayList<>();
//           List<DetailSubjectResponse> detailSubjectContent = curriculumMapper.findAllContent(course.getId(), detail());
//
            //curriculumResponse.setListContent(detailSubjectContent);
//        }

        List<Curriculum> curriculumList = curriculumRepository.findAllBycourse_id(course);
        List<CurriculumResponse> curriculumResponseList = new ArrayList<>();

        for(Curriculum curriculum : curriculumList){
            CurriculumResponse curriculumResponse = new CurriculumResponse();
            curriculumResponse.setCourse_id(curriculum.getCourse().getId());
            curriculumResponse.setCurriculum_id(curriculum.getId());
            curriculumResponse.setSubject(curriculum.getSubject());
            curriculumResponse.setTime(curriculum.getTime());
            curriculumResponse.setStartDate(curriculum.getStartDate());
            curriculumResponse.setEndDate(curriculum.getEndDate());
            for(DetailSubject dto : curriculum.getDetailSubjects()){
                DetailSubjectResponse detailSubjectResponse = new DetailSubjectResponse();
                detailSubjectResponse.setCurriculum_id(curriculum.getId());
                detailSubjectResponse.setId(dto.getId());
                detailSubjectResponse.setContent(dto.getContent());
                curriculumResponse.getContent().add(detailSubjectResponse);
            }
            curriculumResponseList.add(curriculumResponse);
        }
        System.out.println(curriculumResponseList);
        System.out.println("herererere");
        System.out.println(curriculumResponseList.get(0));


        return curriculumResponseList;
    }

    //커리큘럼 하나 불러오기
    @Transactional
    public CurriculumResponse readCurriculum(Long course, Long curriculum) {
        Curriculum curriculum1 = curriculumRepository.findByCourseIdAndId(course, curriculum);

        List<DetailSubjectResponse> contents = new ArrayList<>();
        for(DetailSubject detailSubject : curriculum1.getDetailSubjects()) {
           DetailSubjectResponse detailSubjectResponse = DetailSubjectResponse.builder()
                   .curriculum_id(curriculum)
                   .id(detailSubject.getId())
                   .content(detailSubject.getContent())
                   .build();
            contents.add(detailSubjectResponse);
        }

        return CurriculumResponse.builder()
                .course_id(curriculum1.getCourse().getId())
                .curriculum_id(curriculum1.getId())
                .subject(curriculum1.getSubject())
                .time(curriculum1.getTime())
                .startDate(curriculum1.getStartDate())
                .endDate(curriculum1.getEndDate())
                .content(contents)
                .listOrder(curriculum1.getListOrder())
                .build();


    }


    //커리큘럼 업데이트
    @Transactional
    public void editCurriculum(CurriculumRequest request) throws Exception {

        //데이터 불러와서 보여주기
        curriculumMapper.findCurriculum(request.getId());
        System.out.println("request = " + request);


        //데이터 수정
        curriculumMapper.updateCurriculum(request.getId(), request.getSubject(), request.getTime(), request.getStartDate(), request.getEndDate());
        curriculumMapper.deleteDetailSubject(request.getId());
        curriculumMapper.insertDetailSubject(request.getId(), request.getContents());



//        List<CurriculumResponse> aaaa =curriculumMapper.updateCurriculum(request.getId());
//        return curriculumMapper.updateCurriculum(aaaa);
    }

    //커리큘럼 삭제하기
    @Transactional
    public void deleteCurriculum(Long id) {
        Curriculum curriculum = curriculumRepository.findById(id).orElseThrow(null);
        curriculum.getDetailSubjects().forEach(detailSubject -> {
            DetailSubject detailSubject1 = detailSubjectRepository.findById(detailSubject.getId()).orElseThrow(IllegalArgumentException::new);
            detailSubjectRepository.delete((detailSubject1));
        });

        curriculumRepository.delete(curriculum);
    }
}

        /*Map<String, Map<String, Object>> curriculumInfo = new HashMap<>();

        for(Curriculum cur : curriculumRepository.findAllByCourse(course)){
            Map<String, Object> subjectInfo = new HashMap<>();
            subjectInfo.put("time", cur.getTime());
            subjectInfo.put("startDate", cur.getStartDate());
            subjectInfo.put("endDate", cur.getEndDate());

            List<String> detailSubjectNames = new ArrayList<>();
            for(DetailSubject detailSubject : cur.getDetailSubjects()) {
                detailSubjectNames.add(detailSubject.getContent());
            }

            subjectInfo.put("detailSubjects", detailSubjectNames);
            curriculumInfo.put(cur.getSubject(), subjectInfo);
        }
*/
        /*return ResponseEntity.status(HttpStatus.OK).body(
                CurriculumResponse.builder()
                        .curriculum(curriculumInfo)
                        .build());*/

