package com.zoyasplanet.englishapp.mapper;

import com.zoyasplanet.englishapp.dto.TaskDTO;
import com.zoyasplanet.englishapp.entity.Task;
import org.springframework.stereotype.Component;


@Component
public class TaskMapper {

    public Task toEntity(TaskDTO taskDTO) {
        Task task = new Task();
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setLink(taskDTO.getLink());
        task.setStatus(taskDTO.getStatus());
        task.setPaymentAmount(taskDTO.getPaymentAmount());
        task.setPaymentDueDate(taskDTO.getPaymentDueDate());
        return task;
    }

    public TaskDTO toDTO(Task task) {
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

    public void updateEntity(Task task, TaskDTO taskDTO) {
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setLink(taskDTO.getLink());
        task.setStatus(taskDTO.getStatus());
        task.setPaymentAmount(taskDTO.getPaymentAmount());
        task.setPaymentDueDate(taskDTO.getPaymentDueDate());
        // Не трогаем user, чтобы сохранить связь
    }
}
