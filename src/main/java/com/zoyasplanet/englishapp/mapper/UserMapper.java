package com.zoyasplanet.englishapp.mapper;

import com.zoyasplanet.englishapp.dto.TaskDTO;
import com.zoyasplanet.englishapp.dto.UserDTO;
import com.zoyasplanet.englishapp.entity.Task;
import com.zoyasplanet.englishapp.entity.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.stream.Collectors;


@Component
public class UserMapper {

    public User toEntity(UserDTO userDTO) {
        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setRole(userDTO.getRole());
        return user;
    }

    public UserDTO toDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setUsername(user.getUsername());
        userDTO.setPassword(user.getPassword());
        userDTO.setRole(user.getRole());
        userDTO.setTasks(user.getTasks() != null ? user.getTasks().stream()
                .map(this::toTaskDTO)
                .collect(Collectors.toList()) : new ArrayList<>());
        return userDTO;
    }

    public void updateEntity(User user, UserDTO userDTO) {
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setRole(userDTO.getRole());
        // Не трогаем tasks, чтобы сохранить существующие данные
    }

    // Вспомогательный метод для маппинга связанных задач
    private TaskDTO toTaskDTO(Task task) {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(task.getId());
        taskDTO.setTitle(task.getTitle());
        taskDTO.setDescription(task.getDescription());
        taskDTO.setLink(task.getLink());
        taskDTO.setStatus(task.getStatus());
        taskDTO.setPaymentAmount(task.getPaymentAmount());
        taskDTO.setPaymentDueDate(task.getPaymentDueDate());
        taskDTO.setUserId(task.getUser() != null ? task.getUser().getId() : null);
        return taskDTO;
    }
}
