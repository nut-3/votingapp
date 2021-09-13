package com.github.nut3.votingapp.repository;

import com.github.nut3.votingapp.model.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
public interface UserRepository extends BaseRepository<User> {

    @Cacheable(value = "users", sync = true)
    Optional<User> getByEmail(String email);
}