package com.preparation.prep.controller;

import com.preparation.prep.dto.TaskDTO;
import com.preparation.prep.response.APIResponseWrapper;
import com.preparation.prep.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;



    @PostMapping()
    public ResponseEntity<APIResponseWrapper<TaskDTO>> saveTask(@Valid @RequestBody TaskDTO taskDTO)
    {

        TaskDTO savedTask=taskService.saveTask(taskDTO);

            APIResponseWrapper<TaskDTO> response = new APIResponseWrapper<>("Successfully saved the task", savedTask, true);
            return ResponseEntity.ok(response);

    }

    @GetMapping()
    public ResponseEntity<APIResponseWrapper<List<TaskDTO>>> getAllTasks()
    {
        List <TaskDTO> allTasksList=taskService.getAllTasks();
        APIResponseWrapper<List<TaskDTO>>response=new APIResponseWrapper<>("Successfully fetched all the tasks",allTasksList,true );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponseWrapper<TaskDTO>> getTaskById(@PathVariable long id)
    {
        TaskDTO getTask=taskService.getTaskById(id);


            APIResponseWrapper<TaskDTO> response = new APIResponseWrapper<>("Successfully fetched the task", getTask, true);
            return ResponseEntity.ok(response);


    }

    @PutMapping("/{id}")
    public ResponseEntity<APIResponseWrapper<TaskDTO>> updateTask(@PathVariable long id, @Valid @RequestBody TaskDTO taskDTO)
    {
        TaskDTO updatedTask= taskService.updateTask(id,taskDTO);

        APIResponseWrapper<TaskDTO> response=new APIResponseWrapper<>("Successfully updated the task",updatedTask,true);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponseWrapper<TaskDTO>> deleteTask(@PathVariable long id)
    {
        taskService.deleteTask(id);

        APIResponseWrapper<TaskDTO> response=new APIResponseWrapper<>("Successfully deleted the task",null,true);

        return  ResponseEntity.ok(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String>handleValidation(MethodArgumentNotValidException ex)
    {


        return ResponseEntity.status(500).body(ex.getMessage());
    }








}
