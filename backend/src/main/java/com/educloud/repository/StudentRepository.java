package com.educloud.repository;

import com.educloud.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    boolean existsByStudentId(String studentId);
    boolean existsByEmail(String email);
    boolean existsByRegisterNumber(String registerNumber);
}
