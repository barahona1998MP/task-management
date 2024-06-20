package com.mplata.task_management.controller;

import com.mplata.task_management.dto.TaskDTO;
import com.mplata.task_management.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.config.Task;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/task")
public class TaskController {
    
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/sayHello")
    public String sayHello() {
        return "Hello from API Task";
    }

    @GetMapping()
    public ResponseEntity<List<TaskDTO>> findAll() {
        return ResponseEntity.ok(taskService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> findById(@PathVariable Long id) {
        TaskDTO taskDTO = taskService.findById(id);
        if (taskDTO.getId() == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(taskDTO);
    }

    @PostMapping
    public ResponseEntity<TaskDTO> createTask(@RequestBody @Valid TaskDTO taskDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.save(taskDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> updateTask(@RequestBody TaskDTO taskDTO, @PathVariable Long id) {
        TaskDTO task = taskService.findById(id);
        if(task.getId() == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(taskService.update(taskDTO, id));
    }

}
