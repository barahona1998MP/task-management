package com.mplata.task_management.repository;

import com.mplata.task_management.entity.Task;
import com.mplata.task_management.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
  List<Task> findByUser(User user);
}
