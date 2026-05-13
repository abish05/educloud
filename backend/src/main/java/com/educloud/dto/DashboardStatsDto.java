package com.educloud.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DashboardStatsDto {
    private long totalStudents;
    private long totalFaculty;
    private double averageAttendance;
    private double averageMarks;
}
