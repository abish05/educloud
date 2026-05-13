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
                Department cse = createDept("Computer Science and Engineering", "CSE");
                Department ece = createDept("Electronics and Communication", "ECE");
                Department mech = createDept("Mechanical Engineering", "MECH");
                Department civil = createDept("Civil Engineering", "CIVIL");

                createSubject("Data Structures", "CS301", cse, 3);
                createSubject("Operating Systems", "CS402", cse, 4);
                createSubject("Digital Circuits", "EC201", ece, 2);
                createSubject("Microprocessors", "EC503", ece, 5);
                createSubject("Thermodynamics", "ME302", mech, 3);
                createSubject("Structural Analysis", "CE401", civil, 4);
            }
        } catch (Exception e) {
            System.err.println("Database seeding failed: " + e.getMessage());
        }
    }

    private Department createDept(String name, String code) {
        Department d = new Department();
        d.setName(name);
        d.setCode(code);
        return departmentRepository.save(d);
    }

    private void createSubject(String name, String code, Department d, int sem) {
        Subject s = new Subject();
        s.setName(name);
        s.setCode(code);
        s.setDepartment(d);
        s.setSemester(sem);
        subjectRepository.save(s);
    }
}
