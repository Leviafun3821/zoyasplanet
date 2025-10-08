package com.zoyasplanet.englishapp.repository;

import com.zoyasplanet.englishapp.entity.Task;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TaskRepository extends BaseRepository<Task, Long> {
    List<Task> findByUserId(Long userId); // Новый метод
}
