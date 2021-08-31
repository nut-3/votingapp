package ru.topjava.votingapp.repository;

import org.springframework.transaction.annotation.Transactional;
import ru.topjava.votingapp.model.Vote;

@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {
}
