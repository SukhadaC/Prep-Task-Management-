package com.preparation.prep.dto;


import lombok.Data;

@Data
public class TaskDTO {

    private long id;
    private String title;
    private  String description;
    private String status;
}
