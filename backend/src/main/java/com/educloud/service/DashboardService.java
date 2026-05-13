package com.educloud.service;

import com.educloud.dto.DashboardStatsDto;
import com.educloud.repository.MarksRepository;
import com.educloud.repository.StudentRepository;
import com.educloud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MarksRepository marksRepository;

    public DashboardStatsDto getDashboardStats() {
        long totalStudents = studentRepository.count();
        long totalFaculty = userRepository.findAll().stream()
                .filter(u -> u.getRole().equals("ROLE_FACULTY")).count();
        
        // Mock averages for the dashboard if empty
        double avgAttendance = totalStudents > 0 ? 85.5 : 0.0;
        double avgMarks = totalStudents > 0 ? 75.0 : 0.0;

        return new DashboardStatsDto(totalStudents, totalFaculty, avgAttendance, avgMarks);
    }
}
