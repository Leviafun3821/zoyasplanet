package com.zoyasplanet.englishapp.repository;

import com.zoyasplanet.englishapp.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends BaseRepository<User, Long> {

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.tasks")
    List<User> findAllWithTasks();

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.tasks WHERE u.id = ?1")
    Optional<User> findByIdWithTasks(Long id);
}
