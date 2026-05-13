package com.educloud.config;

import com.educloud.entity.Department;
import com.educloud.entity.Subject;
import com.educloud.entity.User;
import com.educloud.repository.DepartmentRepository;
import com.educloud.repository.SubjectRepository;
import com.educloud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private DepartmentRepository departmentRepository;
    
    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        try {
            // Seed Admin
            if (!userRepository.existsByEmail("admin@educloud.com")) {
                User admin = new User();
                admin.setName("System Admin");
                admin.setEmail("admin@educloud.com");
                admin.setPassword(passwordEncoder.encode("Admin123"));
                admin.setRole("ROLE_ADMIN");
                admin.setActive(true);
                userRepository.save(admin);
            }

            // Seed Faculty
            if (!userRepository.existsByEmail("faculty@educloud.com")) {
                User faculty = new User();
                faculty.setName("Faculty Member");
                faculty.setEmail("faculty@educloud.com");
                faculty.setPassword(passwordEncoder.encode("Faculty123"));
                faculty.setRole("ROLE_FACULTY");
                faculty.setActive(true);
                userRepository.save(faculty);
            }

            // Seed Departments and Subjects if empty
            if (departmentRepository.count() == 0) {
                Department cse = new Department();
                cse.setName("Computer Science and Engineering");
                cse.setCode("CSE");
                cse = departmentRepository.save(cse);

                Subject dsa = new Subject();
                dsa.setName("Data Structures");
                dsa.setCode("CS301");
                dsa.setDepartment(cse);
                dsa.setSemester(3);
                subjectRepository.save(dsa);
            }
        } catch (Exception e) {
            System.err.println("Database seeding failed: " + e.getMessage());
            // Don't rethrow, let the app start even if seeding fails once
        }
    }
}
