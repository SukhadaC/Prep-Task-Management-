package com.preparation.prep.controller;

import com.preparation.prep.dto.TaskDTO;
import com.preparation.prep.entity.Task;
import com.preparation.prep.exception.ResourceNotFoundException;
import com.preparation.prep.service.TaskService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api")
public class MainController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/hello")
    public String sayHello()
    {
        return "Hello from my Preparation";
    }


    @PostMapping("/saveTask")
    public TaskDTO saveTask(@RequestBody TaskDTO taskDTO) throws MethodArgumentNotValidException
    {

            return taskService.saveTask(taskDTO);

    }

    @GetMapping("/getTasks")
    public List<TaskDTO> getAllTasks()
    {
        return taskService.getAllTask();
    }

    @GetMapping("/getTask/{id}")
    public TaskDTO getTaskById(@PathVariable long id)
    {
        return taskService.getTaskById(id);
    }

    @PutMapping("/updateTask/{id}")
    public TaskDTO updateTask(@PathVariable long id, @RequestBody TaskDTO taskDTO)
    {
        return taskService.updateTask(id,taskDTO);
    }

    @DeleteMapping("/deleteTask/{id}")
    public void deleteTask(@PathVariable long id)
    {

        taskService.deleteTask(id);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String>handleValidation(MethodArgumentNotValidException ex)
    {


        return ResponseEntity.status(500).body(ex.getMessage());
    }








}
