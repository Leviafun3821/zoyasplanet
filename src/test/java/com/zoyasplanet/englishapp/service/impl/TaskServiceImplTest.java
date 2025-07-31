package com.zoyasplanet.englishapp.service.impl;

import com.zoyasplanet.englishapp.dto.TaskDTO;
import com.zoyasplanet.englishapp.entity.Task;
import com.zoyasplanet.englishapp.entity.User;
import com.zoyasplanet.englishapp.repository.TaskRepository;
import com.zoyasplanet.englishapp.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createTask_Success() {
        // Подготовка данных
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTitle("Test Task");
        taskDTO.setUserId(userId);

        Task savedTask = new Task();
        savedTask.setId(1L);
        savedTask.setTitle("Test Task");
        savedTask.setUser(user);

        // Моки
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(taskRepository.save(any(Task.class))).thenReturn(savedTask);

        // Вызов метода
        TaskDTO result = taskService.createTask(taskDTO);

        // Проверка
        assertEquals("Test Task", result.getTitle());
        assertEquals(1L, result.getUserId());
    }
}
