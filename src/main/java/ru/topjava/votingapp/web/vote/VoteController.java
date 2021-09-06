package ru.topjava.votingapp.web.vote;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.topjava.votingapp.repository.VoteRepository;
import ru.topjava.votingapp.to.VoteTo;

import java.time.LocalDate;
import java.util.List;

import static ru.topjava.votingapp.util.VoteUtil.createListTos;

@RestController
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class VoteController {

    static final String REST_URL = "/api/votes";

    @Autowired
    private VoteRepository voteRepository;

    @Transactional(readOnly = true)
    @GetMapping
    public List<VoteTo> getVotes(@Nullable @RequestParam Integer restaurantId,
                                 @Nullable @RequestParam Integer userId,
                                 @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam LocalDate from,
                                 @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam LocalDate to) {
        log.info("Vote history from {} to {} ", from, to);
        return createListTos(voteRepository.getVotes(restaurantId, userId, from, to));
    }
}
