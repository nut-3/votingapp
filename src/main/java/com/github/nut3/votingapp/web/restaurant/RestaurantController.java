package com.github.nut3.votingapp.web.restaurant;

import com.github.nut3.votingapp.model.Restaurant;
import com.github.nut3.votingapp.repository.RestaurantRepository;
import com.github.nut3.votingapp.to.RestaurantTo;
import com.github.nut3.votingapp.util.RestaurantUtility;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Clock;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = RestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@CacheConfig(cacheNames = "restaurants")
@AllArgsConstructor
public class RestaurantController {

    static final String REST_URL = "/api/restaurants";

    private RestaurantRepository restaurantRepository;
    private Clock clock;

    @Cacheable(sync = true)
    @GetMapping
    public List<Restaurant> getAll() {
        log.info("get all restaurants");
        return restaurantRepository.findAll();
    }

    @Cacheable(sync = true)
    @GetMapping("/with-menu")
    public List<RestaurantTo> getAllWithMenu() {
        log.info("get all restaurants with their menus");
        LocalDate today = LocalDate.now(clock);
        List<Restaurant> restaurants = restaurantRepository.getAllByMenusDate(today);
        return RestaurantUtility.createListTo(restaurants);
    }

    @Cacheable(sync = true)
    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> get(@PathVariable int id) {
        log.info("get restaurant {}", id);
        return ResponseEntity.of(restaurantRepository.findById(id));
    }

    @Cacheable(sync = true)
    @GetMapping("/{id}/with-menu")
    public ResponseEntity<RestaurantTo> getWithMenu(@PathVariable int id) {
        log.info("get restaurant {} with menu", id);
        LocalDate today = LocalDate.now(clock);
        Restaurant restaurant = restaurantRepository.getByIdAndMenusDate(id, today);
        return ResponseEntity.of(RestaurantUtility.createOptionalTo(restaurant));
    }
}
