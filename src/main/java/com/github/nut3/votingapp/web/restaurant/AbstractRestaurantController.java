package com.github.nut3.votingapp.web.restaurant;

import com.github.nut3.votingapp.config.AppConfig;
import com.github.nut3.votingapp.error.TooLateException;
import com.github.nut3.votingapp.model.Restaurant;
import com.github.nut3.votingapp.model.User;
import com.github.nut3.votingapp.model.Vote;
import com.github.nut3.votingapp.repository.RestaurantRepository;
import com.github.nut3.votingapp.repository.VoteRepository;
import com.github.nut3.votingapp.repository.projection.RestaurantRatingView;
import com.github.nut3.votingapp.to.RestaurantTo;
import com.github.nut3.votingapp.util.RestaurantUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
public abstract class AbstractRestaurantController {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    protected Clock clock;

    @Transactional(readOnly = true)
    public List<RestaurantTo> getAll() {
        log.info("getAll restaurants");
        LocalDate today = LocalDate.now(clock);
        List<Restaurant> restaurants = restaurantRepository.getAllByMenusDate(today);
        List<RestaurantRatingView> ratings = voteRepository.countByDateGroupByRestaurantId(today);
        return RestaurantUtility.createListTo(restaurants);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<RestaurantTo> get(int id) {
        log.info("get restaurant {}", id);
        LocalDate today = LocalDate.now(clock);
        Restaurant restaurant = restaurantRepository.getByIdAndMenusDate(id, today);
        int rating = voteRepository.countByRestaurantIdAndDate(id, today);
        return ResponseEntity.of(RestaurantUtility.createOptionalTo(restaurant));
    }

    public void delete(int id) {
        log.info("delete restaurant {}", id);
        restaurantRepository.deleteExisted(id);
    }

    @Transactional
    public Restaurant save(Restaurant restaurant) {
        log.info("save restaurant {}", restaurant);
        return restaurantRepository.save(restaurant);
    }

    @Transactional
    public void vote(int id, User user) {
        LocalDate today = LocalDate.now(clock);
        if (LocalTime.now(clock).isAfter(AppConfig.VOTE_TIME_LIMIT)) {
            throw new TooLateException("Voting is only available before " + AppConfig.VOTE_TIME_LIMIT.format(DateTimeFormatter.ISO_LOCAL_TIME));
        }
        log.info("user {} voted for restaurant {}", user.id(), id);
        Restaurant restaurant = restaurantRepository.getById(id);
        Vote vote = voteRepository.getByUserIdAndDate(user.id(), today)
                .orElse(new Vote(user, today));
        vote.setRestaurant(restaurant);
        voteRepository.save(vote);
    }
}
