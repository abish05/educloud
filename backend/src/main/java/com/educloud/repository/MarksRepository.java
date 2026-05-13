package com.educloud.repository;

import com.educloud.entity.Marks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarksRepository extends JpaRepository<Marks, Long> {
    List<Marks> findByStudentId(Long studentId);
    List<Marks> findAllByOrderByIdDesc();
    java.util.Optional<Marks> findByStudentIdAndSubjectIdAndSemester(Long studentId, Long subjectId, Integer semester);
}
