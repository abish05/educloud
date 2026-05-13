package com.educloud.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class DashboardStatsDto {
    private long totalStudents;
    private long totalFaculty;
    private double averageAttendance;
    private double averageMarks;
    private java.util.Map<String, Long> deptDistribution;
    private List<ActivityLogDto> recentActivities;
}
