package com.mplata.task_management.security.service;

import com.mplata.task_management.security.model.AuthRequest;
import com.mplata.task_management.security.model.AuthResponse;
import com.mplata.task_management.dto.UserDTO;

public interface AuthService {

    AuthResponse register(UserDTO userDTO);

    AuthResponse authenticate(AuthRequest request);
}
