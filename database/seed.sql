USE educloud_db;

-- Passwords are "Admin123" and "Faculty123" respectively (BCrypt hashed)
INSERT INTO users (name, email, password, role) VALUES 
('System Admin', 'admin@educloud.com', '$2a$10$7Z25tY4j6680G6Z.X3/JauoP/5bNq9M.6WpM1p3B0A/s8Z.6K59B.', 'ROLE_ADMIN'),
('Faculty Member', 'faculty@educloud.com', '$2a$10$p3K/3/V0A/H1H/H7p.E/o.M0N/N.9/J/7/6/7p9N.N.6.P5q9/1G.', 'ROLE_FACULTY');

INSERT INTO departments (name, code) VALUES 
('Computer Science and Engineering', 'CSE'),
('Electronics and Communication', 'ECE'),
('Mechanical Engineering', 'MECH');

INSERT INTO subjects (name, code, department_id, semester) VALUES 
('Data Structures', 'CS301', 1, 3),
('Database Systems', 'CS302', 1, 3),
('Digital Logic', 'EC301', 2, 3),
('Thermodynamics', 'ME301', 3, 3);

INSERT INTO students (student_id, name, register_number, email, phone, department_id, year, gender, dob, address, attendance_percentage) VALUES 
('STU001', 'John Doe', 'REG2023001', 'john.doe@example.com', '9876543210', 1, 2, 'Male', '2004-05-10', '123 Tech St, City', 85.5),
('STU002', 'Jane Smith', 'REG2023002', 'jane.smith@example.com', '9876543211', 1, 2, 'Female', '2004-06-15', '456 Logic Ave, City', 92.0),
('STU003', 'Alice Johnson', 'REG2023003', 'alice.j@example.com', '9876543212', 2, 2, 'Female', '2004-02-20', '789 Circuit Blvd, City', 78.5),
('STU004', 'Bob Brown', 'REG2023004', 'bob.b@example.com', '9876543213', 3, 2, 'Male', '2003-11-05', '321 Auto Rd, City', 65.0);

INSERT INTO attendance (student_id, subject_id, date, status) VALUES 
(1, 1, '2026-05-10', 'PRESENT'),
(1, 2, '2026-05-10', 'PRESENT'),
(2, 1, '2026-05-10', 'PRESENT'),
(2, 2, '2026-05-10', 'ABSENT');

INSERT INTO marks (student_id, subject_id, semester, internal_marks, semester_marks, gpa) VALUES 
(1, 1, 3, 45.0, 85.0, 8.5),
(1, 2, 3, 42.0, 78.0, 7.8),
(2, 1, 3, 48.0, 92.0, 9.2),
(2, 2, 3, 40.0, 70.0, 7.0);

INSERT INTO activity_logs (user_id, action) VALUES 
(1, 'System Initialized'),
(1, 'Added test data for students and subjects');
