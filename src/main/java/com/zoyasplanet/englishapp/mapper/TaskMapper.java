package com.zoyasplanet.englishapp.mapper;

import com.zoyasplanet.englishapp.dto.TaskDTO;
import com.zoyasplanet.englishapp.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TaskMapper {

    TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);

    @Mapping(target = "userId", source = "user.id")
    TaskDTO toDto(Task task);

    @Mapping(target = "user", ignore = true)
    Task toEntity(TaskDTO taskDTO);

    @Mapping(target = "id", ignore = true) // Добавляем, чтобы сохранить id
    @Mapping(target = "user", ignore = true)
    void updateEntity(@MappingTarget Task task, TaskDTO taskDTO);

}
