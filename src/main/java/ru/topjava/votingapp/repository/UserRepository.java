package ru.topjava.votingapp.repository;

import org.springframework.transaction.annotation.Transactional;
import ru.topjava.votingapp.model.User;

import java.util.Optional;

@Transactional(readOnly = true)
public interface UserRepository extends BaseRepository<User> {

    Optional<User> getByEmail(String email);
}