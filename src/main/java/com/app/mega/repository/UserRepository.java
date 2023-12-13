package com.app.mega.repository;

import com.app.mega.entity.Course;
import com.app.mega.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//어노테이션이 없어도 JpaRepository를 상속하면 IoC가 자동으로 등록됨.
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    List<User> findAllByCourse(Course course);
}