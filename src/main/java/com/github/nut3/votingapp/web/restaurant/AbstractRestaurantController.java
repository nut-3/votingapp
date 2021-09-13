package com.github.nut3.votingapp.web.restaurant;

import com.github.nut3.votingapp.model.Restaurant;
import com.github.nut3.votingapp.repository.RestaurantRepository;
import com.github.nut3.votingapp.to.RestaurantTo;
import com.github.nut3.votingapp.util.RestaurantUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDate;
import java.util.List;

@Slf4j
public abstract class AbstractRestaurantController {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    protected Clock clock;

    public List<Restaurant> getAll() {
        log.info("get all restaurants");
        return restaurantRepository.findAll();
    }

    public List<RestaurantTo> getAllWithMenu() {
        log.info("get all restaurants with their menus");
        LocalDate today = LocalDate.now(clock);
        List<Restaurant> restaurants = restaurantRepository.getAllByMenusDate(today);
        return RestaurantUtility.createListTo(restaurants);
    }

    public ResponseEntity<Restaurant> get(int id) {
        log.info("get restaurant {}", id);
        return ResponseEntity.of(restaurantRepository.findById(id));
    }

    public ResponseEntity<RestaurantTo> getWithMenu(int id) {
        log.info("get restaurant {} with menu", id);
        LocalDate today = LocalDate.now(clock);
        Restaurant restaurant = restaurantRepository.getByIdAndMenusDate(id, today);
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
}
