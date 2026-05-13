package com.educloud.controller;

import com.educloud.dto.AuthRequest;
import com.educloud.dto.AuthResponse;
import com.educloud.entity.ActivityLog;
import com.educloud.entity.User;
import com.educloud.repository.ActivityLogRepository;
import com.educloud.repository.UserRepository;
import com.educloud.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ActivityLogRepository activityLogRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
        );

        if (authentication.isAuthenticated()) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String token = jwtUtil.generateToken(userDetails);
            
            User user = userRepository.findByEmail(authRequest.getEmail()).orElseThrow();
            String role = user.getRole();
            
            activityLogRepository.save(new ActivityLog(user, "User logged in successfully"));
            
            return ResponseEntity.ok(new AuthResponse(token, user.getEmail(), role, user.getName()));
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }
}
