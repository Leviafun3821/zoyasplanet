package com.zoyasplanet.englishapp.service.impl;

import com.zoyasplanet.englishapp.dto.TaskDTO;
import com.zoyasplanet.englishapp.entity.Task;
import com.zoyasplanet.englishapp.entity.User;
import com.zoyasplanet.englishapp.mapper.TaskMapper;
import com.zoyasplanet.englishapp.repository.TaskRepository;
import com.zoyasplanet.englishapp.repository.UserRepository;
import com.zoyasplanet.englishapp.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public TaskDTO createTask(TaskDTO taskDTO) {
        User user = userRepository.findById(taskDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + taskDTO.getUserId()));

        Task task = taskMapper.toEntity(taskDTO);
        task.setUser(user); // Устанавливаем пользователя
        task = taskRepository.save(task);
        return taskMapper.toDTO(task);
    }

    @Override
    public TaskDTO getTaskById(Long id) {
        return taskRepository.findById(id)
                .map(taskMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));
    }

    @Override
    public List<TaskDTO> getAllTasks() {
        return taskRepository.findAll().stream()
                .map(taskMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TaskDTO updateTask(Long id, TaskDTO taskDTO) {
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));
        Task updatedTask = taskMapper.toEntity(taskDTO);
        updatedTask.setId(existingTask.getId()); // Сохраняем ID
        updatedTask.setUser(existingTask.getUser()); // Сохраняем связь с пользователем
        updatedTask = taskRepository.save(updatedTask);
        return taskMapper.toDTO(updatedTask);
    }

    @Override
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}
