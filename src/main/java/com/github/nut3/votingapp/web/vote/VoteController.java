package com.github.nut3.votingapp.web.vote;

import com.github.nut3.votingapp.config.AppConfig;
import com.github.nut3.votingapp.error.IllegalDateTimeStateException;
import com.github.nut3.votingapp.error.IllegalRequestDataException;
import com.github.nut3.votingapp.model.Vote;
import com.github.nut3.votingapp.to.VoteTo;
import com.github.nut3.votingapp.web.AuthUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.github.nut3.votingapp.util.VoteUtil.createTo;
import static com.github.nut3.votingapp.util.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class VoteController extends AbstractVoteController {

    static final String REST_URL = "/api/votes";

    @Transactional(readOnly = true)
    @GetMapping
    public List<VoteTo> get(@AuthenticationPrincipal AuthUser authUser,
                                 @Nullable @RequestParam Integer restaurantId,
                                 @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam LocalDate from,
                                 @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam LocalDate to) {
        return super.get(authUser.getUser(), restaurantId, from, to);
    }

    @Transactional
    @PostMapping
    public ResponseEntity<VoteTo> createWithLocation(@Valid @RequestBody VoteTo voteTo, @AuthenticationPrincipal AuthUser authUser) {
        checkNew(voteTo);
        Vote created = super.save(null, voteTo.getRestaurant().id(), authUser.id());
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(createTo(created));
    }

    @Transactional
    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void change(@Valid @RequestBody VoteTo voteTo, @AuthenticationPrincipal AuthUser authUser) {
        if (LocalTime.now(clock).isAfter(AppConfig.VOTE_TIME_LIMIT)) {
            throw new IllegalDateTimeStateException("Vote change is available only before " + AppConfig.VOTE_TIME_LIMIT.format(DateTimeFormatter.ISO_LOCAL_TIME));
        }
        Vote vote = voteRepository.getById(voteTo.id());
        if (!voteRepository.getById(voteTo.id()).getDate().equals(LocalDate.now(clock))) {
            throw new IllegalDateTimeStateException("You can only change today's vote!");
        }
        if (vote.getUser().id() != authUser.id()) {
            throw new IllegalRequestDataException("You can only change your own votes!");
        }
        super.save(voteTo.id(), voteTo.getRestaurant().id(), authUser.id());
    }
}
