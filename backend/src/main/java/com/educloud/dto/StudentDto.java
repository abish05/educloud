package com.educloud.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class StudentDto {
    private Long id;
    private String studentId;
    private String name;
    private String registerNumber;
    private String email;
    private String phone;
    private Long departmentId;
    private String departmentName;
    private Integer year;
    private String gender;
    private LocalDate dateOfBirth;
    private String address;
    private Double attendancePercentage;
}
