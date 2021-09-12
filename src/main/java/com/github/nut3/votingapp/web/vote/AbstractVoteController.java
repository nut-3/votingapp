package com.github.nut3.votingapp.web.vote;

import com.github.nut3.votingapp.model.Restaurant;
import com.github.nut3.votingapp.model.User;
import com.github.nut3.votingapp.model.Vote;
import com.github.nut3.votingapp.repository.RestaurantRepository;
import com.github.nut3.votingapp.repository.UserRepository;
import com.github.nut3.votingapp.repository.VoteRepository;
import com.github.nut3.votingapp.to.VoteTo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static com.github.nut3.votingapp.util.VoteUtil.createListTos;

@Slf4j
public class AbstractVoteController {

    @Autowired
    VoteRepository voteRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    Clock clock;

    public List<VoteTo> get(User user, Integer restaurantId, LocalDate from, LocalDate to) {
        log.info("Vote history from {} to {} ", from, to);
        LocalDate fromDate = from == null ? LocalDate.of(1, Month.JANUARY, 1) : from;
        LocalDate toDate = to == null ? LocalDate.of(9999, Month.DECEMBER, 31) : to;
        int userId = user.id();
        if (restaurantId == null) {
            return createListTos(voteRepository.getByUserIdBetweenDates(userId, fromDate, toDate));
        }
        return  createListTos(voteRepository.getByRestaurantIdAndUserIdBetweenDates(restaurantId, userId, fromDate, toDate));
    }

    @Transactional
    public Vote save(Integer voteId, int restaurantId, int userId) {
        Restaurant restaurant = restaurantRepository.getById(restaurantId);
        User repoUser =  userRepository.getById(userId);
        log.info("user {} voted for restaurant {}", userId, restaurant.id());
        return voteRepository.save(new Vote(voteId, LocalDate.now(clock), repoUser, restaurant));
    }
}
