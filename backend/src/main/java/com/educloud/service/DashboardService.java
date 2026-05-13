package com.educloud.service;

import com.educloud.dto.DashboardStatsDto;
import com.educloud.dto.ActivityLogDto;
import com.educloud.entity.User;
import com.educloud.repository.MarksRepository;
import com.educloud.repository.StudentRepository;
import com.educloud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ActivityLogService activityLogService;

    @Autowired
    private MarksRepository marksRepository;

    public DashboardStatsDto getDashboardStats() {
        long totalStudents = studentRepository.count();
        long totalFaculty = userRepository.findAll().stream()
                .filter(u -> u.getRole() != null && u.getRole().equalsIgnoreCase("ROLE_FACULTY")).count();
        
        // Calculate real averages
        Double avgAttendance = studentRepository.findAll().stream()
                .mapToDouble(s -> s.getAttendancePercentage() != null ? s.getAttendancePercentage() : 0.0)
                .average().orElse(0.0);
        
        Double avgMarks = marksRepository.findAll().stream()
                .mapToDouble(m -> m.getGpa() != null ? m.getGpa() * 10 : 0.0)
                .average().orElse(0.0);

        // Department distribution
        Map<String, Long> deptDist = studentRepository.findAll().stream()
                .filter(s -> s.getDepartment() != null)
                .collect(Collectors.groupingBy(s -> s.getDepartment().getCode(), Collectors.counting()));

        return new DashboardStatsDto(
            totalStudents, 
            totalFaculty, 
            Math.round(avgAttendance * 10.0) / 10.0, 
            Math.round(avgMarks * 10.0) / 10.0, 
            deptDist,
            activityLogService.getRecentActivities()
        );
    }
}
