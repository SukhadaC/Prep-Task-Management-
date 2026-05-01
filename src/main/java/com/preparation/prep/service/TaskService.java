package com.preparation.prep.service;

import com.preparation.prep.dto.TaskDTO;
import com.preparation.prep.entity.Task;
import com.preparation.prep.exception.ResourceNotFoundException;
import com.preparation.prep.repository.TaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepo taskRepo;

    private TaskDTO mapEntityToDTO(Task task)
    {
        TaskDTO taskDTO =new TaskDTO();
        taskDTO.setId(task.getId());
        taskDTO.setTitle(task.getTitle());
        taskDTO.setDescription((task.getDescription()));
        taskDTO.setStatus(task.getStatus());
        taskDTO.setCreatedAt(task.getCreatedAt());
        taskDTO.setUpdatedAt(task.getUpdatedAt());

        return taskDTO;
    }

    private Task mapDTOToEntity(TaskDTO taskDTO)
    {
        Task task=new Task();


        task.setStatus(taskDTO.getStatus());
        task.setDescription(taskDTO.getDescription());
        task.setTitle(taskDTO.getTitle());
        task.setCreatedAt(taskDTO.getCreatedAt());
        task.setUpdatedAt(taskDTO.getUpdatedAt());


        return task;
    }


    public TaskDTO saveTask(TaskDTO  taskDTO)
    {
        taskDTO.setCreatedAt(LocalDateTime.now());
        taskDTO.setUpdatedAt(LocalDateTime.now());
       Task saved=taskRepo.save(mapDTOToEntity(taskDTO));
        return  mapEntityToDTO(saved);
    }

    public Page<TaskDTO> getAllTasks(int page, int size)
    {
        Pageable pageable= PageRequest.of(page, size);
        Page<Task>taskPage=taskRepo.findAll(pageable);

        return taskPage.map(this::mapEntityToDTO);
    }

    public TaskDTO  getTaskById(long id)
    {
        Task found =taskRepo.findById(id).orElse(null);
        return mapEntityToDTO(found);
    }

    public TaskDTO updateTask(long id, TaskDTO  taskDTO)
    {
        Task newTask=taskRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("This Task is not found"));

            newTask.setUpdatedAt(LocalDateTime.now());
            newTask.setTitle(taskDTO.getTitle());
            newTask.setDescription(taskDTO.getDescription());
            newTask.setStatus(taskDTO.getStatus());


        Task updatedTask=taskRepo.save(newTask);
        return  mapEntityToDTO(updatedTask);


    }

    public void  deleteTask(long id)
    {

        Task task=taskRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("This task does not exists"));

        taskRepo.delete(task);




    }




}
