# EduCloud - Cloud-Based Student Management System 2026

## Overview
EduCloud is a modern, enterprise-ready full-stack Student Management System. It features a beautiful glassmorphism design, comprehensive backend built with Spring Boot, and uses JWT for secure authentication.

## Technologies Used
- **Frontend**: HTML5, CSS3 (Custom Variables, Flexbox/Grid, Glassmorphism), Vanilla JavaScript, Chart.js
- **Backend**: Java 17, Spring Boot 3.2, Spring Security, JWT (JSON Web Tokens), Spring Data JPA
- **Database**: MySQL 8.0

## Features
- JWT Authentication & Role-based Access Control (Admin/Faculty)
- Modern Dashboard with Analytics (Chart.js)
- Student CRUD operations
- Attendance tracking
- Marks and GPA calculation

## Setup Instructions

### 1. Database Setup
1. Create a MySQL database named `educloud_db`.
2. Ensure your MySQL credentials are `root` for username and `root` for password (or update `application.properties`).
3. Run the SQL scripts in `database/schema.sql` and `database/seed.sql` to initialize tables and data.
   *(Note: The backend application will automatically seed the Admin and Faculty users on startup if they do not exist).*

### 2. Backend Setup
1. Ensure you have Java 17+ and Maven 3.8+ installed.
2. Navigate to the `backend` directory.
3. Run `mvn clean install` to build the application.
4. Run `mvn spring-boot:run` to start the server. It will be available at `http://localhost:8080`.

### 3. Frontend Setup
1. No Node.js or framework setup required! 
2. Simply open `frontend/index.html` in your web browser.
3. For the best experience, use an extension like VS Code "Live Server".

## Test Credentials
- **Admin**: `admin@educloud.com` / `Admin123`
- **Faculty**: `faculty@educloud.com` / `Faculty123`

## AWS Deployment Ready
To deploy on AWS:
1. Create an RDS instance for MySQL and update `application.properties` with the endpoint.
2. Package the backend into a `.jar` file using `mvn clean package`.
3. Deploy the `.jar` to an EC2 instance or Elastic Beanstalk.
4. Host the `frontend` folder on Amazon S3 with Static Website Hosting enabled, or deploy via AWS Amplify.
