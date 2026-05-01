package com.preparation.prep.service;

import com.preparation.prep.dto.TaskDTO;
import com.preparation.prep.entity.Task;
import com.preparation.prep.entity.TaskUser;
import com.preparation.prep.exception.ResourceNotFoundException;
import com.preparation.prep.repository.TaskRepo;
import com.preparation.prep.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.Authentication;
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

    @Autowired
    private UserRepository userRepo;

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


        //find out current logged in user;
        String username=SecurityContextHolder.getContext().getAuthentication().getName();

        if(username==null)
            throw new RuntimeException("User is not found");

        TaskUser user=userRepo.findByUsername(username);
        Task task=mapDTOToEntity(taskDTO);
        task.setUser(user);

        Task saved=taskRepo.save(task);

        return  mapEntityToDTO(saved);
    }





    public List<TaskDTO> getAllTasks()
    {


        String username=SecurityContextHolder.getContext().getAuthentication().getName();


        if(username==null)
            throw new RuntimeException("User Not found");

        TaskUser user=userRepo.findByUsername(username);

        long userid=user.getUserId();

        List<TaskDTO>listTask=new ArrayList<>();

        List<Task>listTaskEntity=taskRepo.findByUserId(userid);

        for(Task task:listTaskEntity)
            listTask.add(mapEntityToDTO(task));

        return  listTask;
    }

    public TaskDTO  getTaskById(long id)
    {

        String username=SecurityContextHolder.getContext().getAuthentication().getName();


        Task found =taskRepo.findById(id).orElse(null);

        if(found==null)
            throw new RuntimeException("Invalid Task Id");

        if(!found.getUser().getUsername().equals(username))
                throw new RuntimeException("This user is not authorized to access this task");

        return mapEntityToDTO(found);
    }

    public TaskDTO updateTask(long id, TaskDTO  taskDTO)
    {

        String username=SecurityContextHolder.getContext().getAuthentication().getName();
        Task newTask=taskRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("This Task is not found"));

        if(!newTask.getUser().getUsername().equals(username))
            throw new RuntimeException("Not Authorized to update!");

        newTask.setUpdatedAt(LocalDateTime.now());
        newTask.setTitle(taskDTO.getTitle());
        newTask.setDescription(taskDTO.getDescription());
        newTask.setStatus(taskDTO.getStatus());



        Task updatedTask=taskRepo.save(newTask);
        return  mapEntityToDTO(updatedTask);


    }

    public void  deleteTask(long id)
    {
        String username=SecurityContextHolder.getContext().getAuthentication().getName();
        Task task=taskRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("This task does not exists"));

        if(!task.getUser().getUsername().equals(username))
            throw new RuntimeException("Not Authorized to delete the user");

        taskRepo.delete(task);




    }




}
