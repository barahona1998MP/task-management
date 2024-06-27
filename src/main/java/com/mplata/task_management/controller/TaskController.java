package com.mplata.task_management.controller;

import com.mplata.task_management.dto.TaskDTO;
import com.mplata.task_management.entity.User;
import com.mplata.task_management.repository.UserRepository;
import com.mplata.task_management.service.TaskService;
import com.mplata.task_management.service.UserService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/task")
public class TaskController {
    
    private final TaskService taskService;
    private final UserRepository userRepository;    
    public TaskController(TaskService taskService, UserRepository userRepository){
        this.taskService = taskService;
        this.userRepository = userRepository;   
    }



    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> findById(@PathVariable Long id) {
        TaskDTO taskDTO = taskService.findById(id);
        if (taskDTO.getId() == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(taskDTO);
    }

    @PostMapping("/{id}")
    public ResponseEntity<TaskDTO> createTaskUser(@PathVariable Long id, @RequestBody TaskDTO taskDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.createTaskUser(id, taskDTO));
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


    @GetMapping("/user")
    public ResponseEntity<List<TaskDTO>> getTaskForUser(Authentication authentication) {
        String userEmail = authentication.getName();
        List<TaskDTO> taskDTOs = taskService.getTaskForUser(userEmail);
        return ResponseEntity.ok(taskDTOs);
    }

    @PostMapping("/user/{id}")
    public ResponseEntity<TaskDTO> saveTask(@RequestBody TaskDTO taskDTO, @PathVariable Long id, Authentication authentication) {
        String userEmail = authentication.getName();
        User user = userRepository.findByEmail(userEmail).get();
        if (user.getId() == id) {
            taskDTO.setId(user.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(taskService.saveTask(taskDTO, id));
        } else {
            return ResponseEntity.notFound().build();
        }
        
    }

    @DeleteMapping("/user/{taskId}")
    public ResponseEntity<Void> deleteTaskForUser(@PathVariable Long taskId,Authentication authentication) {
        String userEmail = authentication.getName();
        boolean isDeleted = taskService.deleteTask(taskId, userEmail);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

}
