package com.github.nut3.votingapp.web.vote;

import com.github.nut3.votingapp.config.AppConfig;
import com.github.nut3.votingapp.error.IllegalDateTimeStateException;
import com.github.nut3.votingapp.model.Restaurant;
import com.github.nut3.votingapp.model.User;
import com.github.nut3.votingapp.model.Vote;
import com.github.nut3.votingapp.repository.RestaurantRepository;
import com.github.nut3.votingapp.repository.UserRepository;
import com.github.nut3.votingapp.repository.VoteRepository;
import com.github.nut3.votingapp.to.VoteTo;
import com.github.nut3.votingapp.web.AuthUser;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.github.nut3.votingapp.util.VoteUtil.createListTos;

@RestController
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Slf4j
public class VoteController {

    static final String REST_URL = "/api/votes";
    private VoteRepository voteRepository;
    private RestaurantRepository restaurantRepository;
    private UserRepository userRepository;
    private Clock clock;

    @Transactional(readOnly = true)
    @GetMapping
    public List<VoteTo> get(@AuthenticationPrincipal AuthUser authUser,
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
        return createListTos(voteRepository.getByRestaurantIdAndUserIdBetweenDates(restaurantId, userId, fromDate, toDate));
    }

    @Transactional
    @PostMapping
    public ResponseEntity<Void> vote(@RequestParam int restaurantId, @AuthenticationPrincipal AuthUser authUser) {
        Restaurant restaurant = restaurantRepository.getById(restaurantId);
        User user = userRepository.getById(authUser.id());
        Vote vote = voteRepository.findByUserIdAndDate(authUser.id(), LocalDate.now(clock))
                .orElse(new Vote(LocalDate.now(clock), user, restaurant));
        if (vote.isNew()) {
            log.info("user {} voted for restaurant {}", authUser.id(), restaurant.id());
            voteRepository.save(vote);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        if (LocalTime.now(clock).isAfter(AppConfig.VOTE_TIME_LIMIT)) {
            throw new IllegalDateTimeStateException("Vote change is available only before " + AppConfig.VOTE_TIME_LIMIT.format(DateTimeFormatter.ISO_LOCAL_TIME));
        }
        log.info("user {} changed vote for restaurant {}", authUser.id(), restaurant.id());
        vote.setRestaurant(restaurant);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
