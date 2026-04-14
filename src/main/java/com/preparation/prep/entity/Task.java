package com.preparation.prep.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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


    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;


}
