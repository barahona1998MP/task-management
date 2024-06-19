package com.mplata.task_management.service;

import com.mplata.task_management.dto.TaskDTO;

import java.util.List;

public interface TaskService {
    List<TaskDTO> findAll();

    TaskDTO findById(Long id);

    TaskDTO save(TaskDTO taskDTO);

    TaskDTO update(TaskDTO taskDTO, Long id);

    String delete(Long id);
}
