package com.mplata.task_management.service;

import com.mplata.task_management.dto.UserDTO;
import com.mplata.task_management.entity.User;
import com.mplata.task_management.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserDTO> findAll() {
        ModelMapper modelMapper = new ModelMapper();
        return userRepository.findAll()
                .stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO findById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            ModelMapper modelMapper = new ModelMapper();
            User currentUser = user.get();
            return modelMapper.map(currentUser, UserDTO.class);
        } else {
            return new UserDTO();
        }
    }
}
