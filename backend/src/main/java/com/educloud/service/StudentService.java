package com.educloud.service;

import com.educloud.dto.StudentDto;
import com.educloud.entity.Department;
import com.educloud.entity.Student;
import com.educloud.exception.ResourceNotFoundException;
import com.educloud.repository.DepartmentRepository;
import com.educloud.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private ActivityLogService activityLogService;

    public List<StudentDto> getAllStudents() {
        return studentRepository.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public StudentDto getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        return mapToDto(student);
    }

    public StudentDto addStudent(StudentDto studentDto) {
        Student student = mapToEntity(studentDto);
        Student saved = studentRepository.save(student);
        activityLogService.logActivity("Added new student: " + saved.getName());
        return mapToDto(saved);
    }

    public void deleteStudent(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        activityLogService.logActivity("Deleted student: " + student.getName());
        studentRepository.deleteById(id);
    }

    public StudentDto updateStudent(Long id, StudentDto studentDto) {
        Student existingStudent = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        
        existingStudent.setName(studentDto.getName());
        existingStudent.setEmail(studentDto.getEmail());
        existingStudent.setPhone(studentDto.getPhone());
        existingStudent.setYear(studentDto.getYear());
        existingStudent.setGender(studentDto.getGender());
        existingStudent.setDateOfBirth(studentDto.getDateOfBirth());
        existingStudent.setAddress(studentDto.getAddress());
        
        if (studentDto.getDepartmentId() != null) {
            Department dept = departmentRepository.findById(studentDto.getDepartmentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Department not found"));
            existingStudent.setDepartment(dept);
        }
        
        Student saved = studentRepository.save(existingStudent);
        activityLogService.logActivity("Updated student details: " + saved.getName());
        return mapToDto(saved);
    }

    private StudentDto mapToDto(Student student) {
        StudentDto dto = new StudentDto();
        dto.setId(student.getId());
        dto.setStudentId(student.getStudentId());
        dto.setName(student.getName());
        dto.setRegisterNumber(student.getRegisterNumber());
        dto.setEmail(student.getEmail());
        dto.setPhone(student.getPhone());
        dto.setYear(student.getYear());
        dto.setGender(student.getGender());
        dto.setDateOfBirth(student.getDateOfBirth());
        dto.setAddress(student.getAddress());
        dto.setAttendancePercentage(student.getAttendancePercentage());
        
        if (student.getDepartment() != null) {
            dto.setDepartmentId(student.getDepartment().getId());
            dto.setDepartmentName(student.getDepartment().getName());
        }
        return dto;
    }

    private Student mapToEntity(StudentDto dto) {
        Student student = new Student();
        student.setStudentId(dto.getStudentId());
        student.setName(dto.getName());
        student.setRegisterNumber(dto.getRegisterNumber());
        student.setEmail(dto.getEmail());
        student.setPhone(dto.getPhone());
        student.setYear(dto.getYear());
        student.setGender(dto.getGender());
        student.setDateOfBirth(dto.getDateOfBirth());
        student.setAddress(dto.getAddress());
        student.setAttendancePercentage(dto.getAttendancePercentage() != null ? dto.getAttendancePercentage() : 0.0);

        if (dto.getDepartmentId() != null) {
            Department dept = departmentRepository.findById(dto.getDepartmentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Department not found"));
            student.setDepartment(dept);
        }
        return student;
    }
}
