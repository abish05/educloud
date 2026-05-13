package com.educloud.dto;

import lombok.Data;

@Data
public class MarksDto {
    private Long id;
    private Long studentId;
    private String studentName;
    private Long subjectId;
    private String subjectName;
    private Integer semester;
    private Double internalMarks;
    private Double semesterMarks;
    private Double gpa;
}
