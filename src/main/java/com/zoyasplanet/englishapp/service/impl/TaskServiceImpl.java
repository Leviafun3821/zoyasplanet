package com.zoyasplanet.englishapp.service.impl;

import com.zoyasplanet.englishapp.dto.TaskDTO;
import com.zoyasplanet.englishapp.entity.Task;
import com.zoyasplanet.englishapp.entity.User;
import com.zoyasplanet.englishapp.exception.TaskNotFoundException;
import com.zoyasplanet.englishapp.exception.UserNotFoundException;
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
    private final UserRepository userRepository;

    @Override
    @Transactional
    public TaskDTO createTask(TaskDTO taskDTO) {
        User user = userRepository.findById(taskDTO.getUserId())
                .orElseThrow(() -> new UserNotFoundException(taskDTO.getUserId()));

        Task task = TaskMapper.INSTANCE.toEntity(taskDTO);
        task.setUser(user); // Устанавливаем пользователя
        task = taskRepository.save(task);
        return TaskMapper.INSTANCE.toDto(task);
    }

    @Override
    public TaskDTO getTaskById(Long id) {
        return taskRepository.findById(id)
                .map(TaskMapper.INSTANCE::toDto)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    @Override
    public List<TaskDTO> getAllTasks() {
        return taskRepository.findAll().stream()
                .map(TaskMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public TaskDTO updateTask(Long id, TaskDTO taskDTO) {
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        TaskMapper.INSTANCE.updateEntity(existingTask, taskDTO); // Обновляем существующий объект
        existingTask = taskRepository.save(existingTask); // Сохраняем изменения
        return TaskMapper.INSTANCE.toDto(existingTask);
    }

    @Override
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskDTO> getTasksByUserId(Long userId) {
        return taskRepository.findByUserId(userId)
                .stream()
                .map(TaskMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

}
