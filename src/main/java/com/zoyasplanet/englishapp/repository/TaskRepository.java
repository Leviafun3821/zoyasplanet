package com.zoyasplanet.englishapp.repository;

import com.zoyasplanet.englishapp.entity.Task;
import org.springframework.stereotype.Repository;


@Repository
public interface TaskRepository extends BaseRepository<Task, Long> {
}
