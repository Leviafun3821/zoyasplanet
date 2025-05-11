package com.zoyasplanet.englishapp.repository;

import com.zoyasplanet.englishapp.entity.User;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends BaseRepository<User, Long> {
}
