package com.educloud.controller;

import com.educloud.dto.MarksDto;
import com.educloud.entity.Marks;
import com.educloud.entity.Student;
import com.educloud.entity.Subject;
import com.educloud.repository.MarksRepository;
import com.educloud.repository.StudentRepository;
import com.educloud.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/marks")
@CrossOrigin(origins = "*")
public class MarksController {

    @Autowired
    private MarksRepository marksRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @PostMapping
    public ResponseEntity<Marks> saveMarks(@RequestBody MarksDto dto) {
        Student student = studentRepository.findById(dto.getStudentId()).orElseThrow();
        Subject subject = subjectRepository.findById(dto.getSubjectId()).orElseThrow();
        
        Marks marks = new Marks();
        marks.setStudent(student);
        marks.setSubject(subject);
        marks.setSemester(dto.getSemester());
        marks.setInternalMarks(dto.getInternalMarks());
        marks.setSemesterMarks(dto.getSemesterMarks());
        marks.setGpa(dto.getGpa());
        
        return ResponseEntity.ok(marksRepository.save(marks));
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Marks>> getStudentMarks(@PathVariable Long studentId) {
        return ResponseEntity.ok(marksRepository.findByStudentId(studentId));
    }
}
