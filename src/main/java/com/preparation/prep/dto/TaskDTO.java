package com.preparation.prep.dto;


import lombok.Data;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Data
public class TaskDTO {

    private long id;
    private String title;
    private  String description;
    private String status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
