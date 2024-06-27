package com.mplata.task_management.service;

import com.mplata.task_management.dto.TaskDTO;

import java.util.List;

public interface TaskService {
    
    List<TaskDTO> getTaskForUser(String username);

    TaskDTO findById(Long id);

    TaskDTO save(TaskDTO taskDTO);

    TaskDTO saveTask(TaskDTO taskDto, Long id); 

    TaskDTO update(TaskDTO taskDTO, Long id);

    String delete(Long id);

    TaskDTO createTaskUser(Long id, TaskDTO taskDTO);

    boolean deleteTask(Long taskId, String userEmail);

    
 }
