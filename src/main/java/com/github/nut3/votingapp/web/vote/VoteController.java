package com.github.nut3.votingapp.web.vote;

import com.github.nut3.votingapp.repository.VoteRepository;
import com.github.nut3.votingapp.to.VoteTo;
import com.github.nut3.votingapp.web.AuthUser;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static com.github.nut3.votingapp.util.VoteUtil.createListTos;

@AllArgsConstructor
@RestController
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class VoteController {

    static final String REST_URL = "/api/votes";

    private VoteRepository voteRepository;

    @Transactional(readOnly = true)
    @GetMapping
    public List<VoteTo> getVotes(@AuthenticationPrincipal AuthUser authUser,
                                 @Nullable @RequestParam Integer restaurantId,
                                 @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam LocalDate from,
                                 @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam LocalDate to) {
        log.info("Vote history from {} to {} ", from, to);

        LocalDate fromDate = from == null ? LocalDate.of(1, Month.JANUARY, 1) : from;
        LocalDate toDate = to == null ? LocalDate.of(9999, Month.DECEMBER, 31) : to;
        int userId = authUser.id();
        if (restaurantId == null) {
            return createListTos(voteRepository.getByUserIdBetweenDates(userId, fromDate, toDate));
        }
        return  createListTos(voteRepository.getByRestaurantIdAndUserIdBetweenDates(restaurantId, userId, fromDate, toDate));
    }
}
