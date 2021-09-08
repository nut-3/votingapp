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
import java.time.Month;
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

        LocalDate fromDate = from == null ? LocalDate.of(1, Month.JANUARY, 1) : from;
        LocalDate toDate = to == null ? LocalDate.of(9999, Month.DECEMBER, 31) : to;
        if (userId == null && restaurantId == null) {
            return createListTos(voteRepository.getBetweenDates(fromDate, toDate));
        } else if (userId == null) {
            return createListTos(voteRepository.getByRestaurantIdBetweenDates(restaurantId, fromDate, toDate));
        } else if (restaurantId == null) {
            return createListTos(voteRepository.getByUserIdBetweenDates(userId, fromDate, toDate));
        }
        return  createListTos(voteRepository.getByRestaurantIdAndUserIdBetweenDates(restaurantId, userId, fromDate, toDate));
    }
}
