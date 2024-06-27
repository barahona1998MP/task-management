package com.mplata.task_management.service;

import com.mplata.task_management.dto.UserDTO;

import java.util.List;

public interface UserService {

    List<UserDTO> findAll();

    UserDTO findById(Long id);
}
