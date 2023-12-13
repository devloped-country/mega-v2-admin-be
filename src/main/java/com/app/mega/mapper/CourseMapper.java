package com.app.mega.mapper;

import com.app.mega.dto.request.CourseRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CourseMapper {
  void saveCourse(@Param("id") Long id, @Param("courseRequest") CourseRequest courseRequest);
}
