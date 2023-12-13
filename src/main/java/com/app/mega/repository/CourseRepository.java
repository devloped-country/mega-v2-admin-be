package com.app.mega.repository;

import com.app.mega.entity.Course;
import com.app.mega.entity.Institution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    Course findByName(String name);
    Course findByNameAndInstitution(String name, Institution institution);
}
