package com.app.mega.repository;

import com.app.mega.entity.DetailSubject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetailSubjectRepository extends JpaRepository<DetailSubject, Long> {


}
