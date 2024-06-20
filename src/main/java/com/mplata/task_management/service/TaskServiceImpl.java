package com.mplata.task_management.service;

import com.mplata.task_management.dto.TaskDTO;
import com.mplata.task_management.entity.Task;
import com.mplata.task_management.repository.TaskRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService{

    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public List<TaskDTO> findAll() {
        ModelMapper modelMapper = new ModelMapper();
        return this.taskRepository.findAll()
                .stream()
                .map(task -> modelMapper.map(task, TaskDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public TaskDTO findById(Long id) {
        Optional<Task> task = taskRepository.findById(id);
        if(task.isPresent()) {
            ModelMapper modelMapper = new ModelMapper();
            Task currentTask = task.get();
            return modelMapper.map(currentTask, TaskDTO.class);
        } else {
            return new TaskDTO();
        }
    }

    @Override
    public TaskDTO save(TaskDTO taskDTO) {
        try {
            ModelMapper modelMapper = new ModelMapper();
            Task task = modelMapper.map(taskDTO, Task.class);
            taskRepository.save(task);
            return taskDTO;
        } catch (Exception e) {
            throw new UnsupportedOperationException("Error saving the user");
        }

    }

    @Override
    public TaskDTO update(TaskDTO taskDTO, Long id) {
        Optional<Task> task = taskRepository.findById(id);
        if(task.isPresent()) {
            Task currentTask = task.get();
            currentTask.setTitle(taskDTO.getTitle());
            currentTask.setDescription(taskDTO.getDescription());
            currentTask.setCompleted(taskDTO.isCompleted());
            this.taskRepository.save(currentTask);
            ModelMapper modelMapper = new ModelMapper();
            return modelMapper.map(currentTask, TaskDTO.class);

        } else {
            throw new UnsupportedOperationException("The user does not exist");
        }
    }

    @Override
    public String delete(Long id) {
        Optional<Task> task = taskRepository.findById(id);
        if(task.isPresent()) {
            Task currentTask = task.get();
            taskRepository.delete(currentTask);
            return "User with ID " +id +" deleted successfully";
        }
        return "The user with ID:"+id +" does not exist";
    }
}
