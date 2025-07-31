package com.zoyasplanet.englishapp.service.impl;

import com.zoyasplanet.englishapp.dto.UserDTO;
import com.zoyasplanet.englishapp.entity.User;
import com.zoyasplanet.englishapp.exception.UserNotFoundException;
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

    @Override
    @Transactional
    public UserDTO createUser(UserDTO userDTO) {
        User user = UserMapper.INSTANCE.toEntity(userDTO);
        User savedUser = userRepository.save(user);
        return UserMapper.INSTANCE.toDTO(savedUser);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO getUserById(Long id) {
        return userRepository.findByIdWithTasks(id)
                .map(UserMapper.INSTANCE::toDTO)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> getAllUsers() {
        return userRepository.findAllWithTasks().stream()
                .map(UserMapper.INSTANCE::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User existingUser = userRepository.findByIdWithTasks(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        UserMapper.INSTANCE.updateEntity(existingUser, userDTO);
        User savedUser = userRepository.save(existingUser);
        return UserMapper.INSTANCE.toDTO(savedUser);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        if(!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
    }
}
