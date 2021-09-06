package ru.topjava.votingapp.web.restaurant;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import ru.topjava.votingapp.error.TooLateException;
import ru.topjava.votingapp.model.Restaurant;
import ru.topjava.votingapp.model.User;
import ru.topjava.votingapp.model.Vote;
import ru.topjava.votingapp.repository.RestaurantRepository;
import ru.topjava.votingapp.repository.VoteRepository;
import ru.topjava.votingapp.repository.projection.RestaurantRatingView;
import ru.topjava.votingapp.to.RestaurantTo;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static ru.topjava.votingapp.config.AppConfig.VOTE_TIME_LIMIT;
import static ru.topjava.votingapp.util.RestaurantUtility.createListTo;
import static ru.topjava.votingapp.util.RestaurantUtility.createOptionalTo;

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
        return createListTo(ratings.stream()
                        .collect(Collectors.toMap(RestaurantRatingView::getRestaurantId,
                                RestaurantRatingView::getRating)),
                restaurants);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<RestaurantTo> get(int id) {
        log.info("get restaurant {}", id);
        LocalDate today = LocalDate.now(clock);
        Restaurant restaurant = restaurantRepository.getByIdAndMenusDate(id, today);
        int rating = voteRepository.countByRestaurantIdAndDate(id, today);
        return ResponseEntity.of(createOptionalTo(rating, restaurant));
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
        if (LocalTime.now(clock).isAfter(VOTE_TIME_LIMIT)) {
            throw new TooLateException("Voting is only available before " + VOTE_TIME_LIMIT.format(DateTimeFormatter.ISO_LOCAL_TIME));
        }
        log.info("user {} voted for restaurant {}", user.id(), id);
        Restaurant restaurant = restaurantRepository.getById(id);
        Vote vote = voteRepository.getByUserIdAndDate(user.id(), today)
                .orElse(new Vote(user, today));
        vote.setRestaurant(restaurant);
        voteRepository.save(vote);
    }
}
