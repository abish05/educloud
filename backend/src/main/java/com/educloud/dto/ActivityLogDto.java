package com.educloud.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivityLogDto {
    private String action;
    private String userName;
    private LocalDateTime timestamp;
}
