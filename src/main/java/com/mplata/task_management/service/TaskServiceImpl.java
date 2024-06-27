package com.mplata.task_management.service;

import com.mplata.task_management.dto.TaskDTO;
import com.mplata.task_management.entity.Task;
import com.mplata.task_management.entity.User;
import com.mplata.task_management.repository.TaskRepository;
import com.mplata.task_management.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService{

    private final TaskRepository taskRepository;
    private final EmailService emailService;
    private final UserRepository userRepository;
    public TaskServiceImpl(TaskRepository taskRepository, EmailService emailService, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.emailService = emailService;
        this.userRepository = userRepository;
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

    @Override
    public TaskDTO createTaskUser(Long id, TaskDTO taskDTO) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            Task task = Task.builder()
                    .title(taskDTO.getTitle())
                    .description(taskDTO.getDescription())
                    .user(user.get())
                    .completed(taskDTO.isCompleted())
                    .build();
            this.taskRepository.save(task);
            ModelMapper modelMapper = new ModelMapper();
            return modelMapper.map(task, TaskDTO.class);
        } else {
            throw new UnsupportedOperationException("The user does not exist");
        }
    }

    @Override
    public List<TaskDTO> getTaskForUser(String username) {
       User user = userRepository.findByEmail(username).get();
       if(user.getId() != null) {
           ModelMapper modelMapper = new ModelMapper();
           return this.taskRepository.findByUser(user)
            .stream()
            .map(task -> modelMapper.map(task, TaskDTO.class))
            .collect(Collectors.toList());
       } else {
        return Collections.emptyList();
       }

    }


    @Override
    public TaskDTO saveTask(TaskDTO taskDto, Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            Task task = Task.builder()
                    .title(taskDto.getTitle())
                    .description(taskDto.getDescription())
                    .user(user.get())
                    .completed(taskDto.isCompleted())
                    .build();
            this.taskRepository.save(task);
            ModelMapper modelMapper = new ModelMapper();
            return modelMapper.map(task, TaskDTO.class);
        } else {
            throw new UnsupportedOperationException("The user does not exist");
        }
    }


    @Override
    public boolean deleteTask(Long taskId, String userEmail) {
        User user = userRepository.findByEmail(userEmail).get();
        Optional<Task> task = taskRepository.findById(taskId); 
        if(task.isPresent() && task.get().getUser().equals(user)) {
            taskRepository.delete(task.get());
            return true;
        } else {
            return false;
        }
    }

}
