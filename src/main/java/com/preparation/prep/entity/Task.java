package com.preparation.prep.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @NotBlank(message="Title is required")
    private  String title;

    private String description;

    @NotBlank(message="Status is required")
    private String status;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private TaskUser user;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;


}
