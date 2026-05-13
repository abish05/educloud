package com.educloud.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class AttendanceDto {
    private Long id;
    private Long studentId;
    private String studentName;
    private Long subjectId;
    private String subjectName;
    private LocalDate date;
    private String status;
}
