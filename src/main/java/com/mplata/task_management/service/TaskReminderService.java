package com.mplata.task_management.service;

import com.mplata.task_management.entity.Task;
import com.mplata.task_management.repository.TaskRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskReminderService {

    private final TaskRepository taskRepository;
    private final EmailService emailService;

    public TaskReminderService(TaskRepository taskRepository, EmailService emailService) {
        this.taskRepository = taskRepository;
        this.emailService = emailService;
    }

    @Scheduled(cron = "0 0 17 * * ?")
    public void sendTaskReminders() {
        List<Task> tasks = taskRepository.findAll();
        for (Task task : tasks) {
            if (!task.isCompleted()) {
                emailService.sendSimpleMessage(
                        task.getUser().getEmail(),
                        "Task Reminder: " + task.getTitle(),
                        "This is a reminder to complete your task: "+task.getDescription()
                );
            }
        }
    }
}
