package com.zoyasplanet.englishapp.service.impl;

import com.zoyasplanet.englishapp.dto.UserDTO;
import com.zoyasplanet.englishapp.entity.User;
import com.zoyasplanet.englishapp.mapper.UserMapper;
import com.zoyasplanet.englishapp.repository.UserRepository;
import com.zoyasplanet.englishapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public UserDTO createUser(UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);
        User savedUser = userRepository.save(user);
        return userMapper.toDTO(savedUser);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO getUserById(Long id) {
        return userRepository.findByIdWithTasks(id)
                .map(userMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> getAllUsers() {
        return userRepository.findAllWithTasks().stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User existingUser = userRepository.findByIdWithTasks(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        userMapper.updateEntity(existingUser, userDTO);
        User savedUser = userRepository.save(existingUser);
        return userMapper.toDTO(savedUser);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        if(!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }
}
