package com.educloud.controller;

import com.educloud.dto.AttendanceDto;
import com.educloud.entity.Attendance;
import com.educloud.entity.Student;
import com.educloud.entity.Subject;
import com.educloud.repository.AttendanceRepository;
import com.educloud.repository.StudentRepository;
import com.educloud.repository.SubjectRepository;
import com.educloud.service.ActivityLogService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private ActivityLogService activityLogService;

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
        
        Attendance saved = attendanceRepository.save(attendance);
        activityLogService.logActivity("Marked attendance for " + student.getName() + " in " + subject.getName());
        
        // Recalculate student attendance percentage
        List<Attendance> allAtt = attendanceRepository.findByStudentId(student.getId());
        if (!allAtt.isEmpty()) {
            long presentCount = allAtt.stream().filter(a -> "PRESENT".equalsIgnoreCase(a.getStatus())).count();
            double percentage = (double) presentCount / allAtt.size() * 100.0;
            student.setAttendancePercentage(Math.round(percentage * 10.0) / 10.0);
            studentRepository.save(student);
        }

        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public ResponseEntity<List<AttendanceDto>> getAllAttendance() {
        return ResponseEntity.ok(attendanceRepository.findAllByOrderByDateDesc().stream()
                .map(a -> {
                    AttendanceDto d = new AttendanceDto();
                    d.setId(a.getId());
                    d.setStudentId(a.getStudent().getId());
                    d.setStudentName(a.getStudent().getName());
                    d.setSubjectId(a.getSubject().getId());
                    d.setSubjectName(a.getSubject().getName());
                    d.setDate(a.getDate());
                    d.setStatus(a.getStatus());
                    return d;
                }).collect(Collectors.toList()));
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Attendance>> getStudentAttendance(@PathVariable Long studentId) {
        return ResponseEntity.ok(attendanceRepository.findByStudentId(studentId));
    }
}
