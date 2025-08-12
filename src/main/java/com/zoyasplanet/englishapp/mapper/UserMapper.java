package com.zoyasplanet.englishapp.mapper;

import com.zoyasplanet.englishapp.dto.TaskDTO;
import com.zoyasplanet.englishapp.dto.UserDTO;
import com.zoyasplanet.englishapp.entity.Task;
import com.zoyasplanet.englishapp.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.Collections;
import java.util.List;


@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "password", ignore = true) // Игнорируем пароль в ответе
    @Mapping(target = "tasks", qualifiedByName = "mapTasks")
    UserDTO toDTO(User user);

    @Mapping(target = "tasks", ignore = true)
    User toEntity(UserDTO userDTO);

    @Mapping(target = "id", ignore = true) // Игнорируем id при обновлении
    @Mapping(target = "tasks", ignore = true)
    void updateEntity(@MappingTarget User user, UserDTO userDTO);

    @Named("mapTasks")
    default List<TaskDTO> mapTasks(List<Task> tasks) {
        if (tasks == null) return Collections.emptyList();
        TaskMapper taskMapper = TaskMapper.INSTANCE;
        return tasks.stream()
                .map(taskMapper::toDto)
                .toList();
    }
}
