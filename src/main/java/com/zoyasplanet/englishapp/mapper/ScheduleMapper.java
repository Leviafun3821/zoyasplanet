package com.zoyasplanet.englishapp.mapper;

import com.zoyasplanet.englishapp.dto.ScheduleDTO;
import com.zoyasplanet.englishapp.entity.Schedule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ScheduleMapper {

    ScheduleMapper INSTANCE = Mappers.getMapper(ScheduleMapper.class);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "teacherId", source = "teacher.id")
    @Mapping(target = "taskId", source = "task.id")
    ScheduleDTO toDto(Schedule schedule);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "teacher", ignore = true)
    @Mapping(target = "task", ignore = true)
    Schedule toEntity(ScheduleDTO scheduleDTO);

    @Mapping(target = "id", ignore = true) // Добавляем, чтобы сохранить id
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "teacher", ignore = true)
    @Mapping(target = "task", ignore = true)
    void updateEntity(@MappingTarget Schedule schedule, ScheduleDTO scheduleDTO);

}
