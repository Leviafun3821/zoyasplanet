package com.zoyasplanet.englishapp.repository;

import com.zoyasplanet.englishapp.entity.Schedule;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends BaseRepository<Schedule, Long>{
    List<Schedule> findByUserId(Long userId); // Новый метод
}
