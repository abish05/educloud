package com.educloud.controller;

import com.educloud.dto.AttendanceDto;
import com.educloud.entity.Attendance;
import com.educloud.entity.Student;
import com.educloud.entity.Subject;
import com.educloud.repository.AttendanceRepository;
import com.educloud.repository.StudentRepository;
import com.educloud.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/attendance")
@CrossOrigin(origins = "*")
public class AttendanceController {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @PostMapping("/mark")
    public ResponseEntity<Attendance> markAttendance(@RequestBody AttendanceDto dto) {
        Student student = studentRepository.findById(dto.getStudentId()).orElseThrow();
        Subject subject = subjectRepository.findById(dto.getSubjectId()).orElseThrow();
        
        Attendance attendance = attendanceRepository.findByStudentIdAndSubjectIdAndDate(
                dto.getStudentId(), dto.getSubjectId(), dto.getDate() != null ? dto.getDate() : LocalDate.now())
                .orElse(new Attendance());
        
        attendance.setStudent(student);
        attendance.setSubject(subject);
        attendance.setDate(dto.getDate() != null ? dto.getDate() : LocalDate.now());
        attendance.setStatus(dto.getStatus());
        
        return ResponseEntity.ok(attendanceRepository.save(attendance));
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Attendance>> getStudentAttendance(@PathVariable Long studentId) {
        return ResponseEntity.ok(attendanceRepository.findByStudentId(studentId));
    }
}
