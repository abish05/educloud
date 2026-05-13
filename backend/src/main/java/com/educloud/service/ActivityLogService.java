package com.educloud.service;

import com.educloud.dto.ActivityLogDto;
import com.educloud.entity.ActivityLog;
import com.educloud.entity.User;
import com.educloud.repository.ActivityLogRepository;
import com.educloud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActivityLogService {

    @Autowired
    private ActivityLogRepository activityLogRepository;

    @Autowired
    private UserRepository userRepository;

    public void logActivity(String action) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email;
        if (principal instanceof UserDetails) {
            email = ((UserDetails) principal).getUsername();
        } else {
            email = principal.toString();
        }

        User user = userRepository.findByEmail(email).orElse(null);
        if (user != null) {
            ActivityLog log = new ActivityLog(user, action);
            activityLogRepository.save(log);
        }
    }

    public List<ActivityLogDto> getRecentActivities() {
        return activityLogRepository.findTop10ByOrderByTimestampDesc().stream()
                .map(log -> new ActivityLogDto(
                        log.getAction(),
                        log.getUser() != null ? log.getUser().getName() : "System",
                        log.getTimestamp()
                ))
                .collect(Collectors.toList());
    }
}
