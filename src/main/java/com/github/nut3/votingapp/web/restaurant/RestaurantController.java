package com.github.nut3.votingapp.web.restaurant;

import com.github.nut3.votingapp.model.Restaurant;
import com.github.nut3.votingapp.to.RestaurantTo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = RestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@CacheConfig(cacheNames = "restaurants")
public class RestaurantController extends AbstractRestaurantController {

    static final String REST_URL = "/api/restaurants";

    @Override
    @Cacheable(sync = true)
    @GetMapping
    public List<Restaurant> getAll() {
        return super.getAll();
    }

    @Override
    @Cacheable(sync = true)
    @GetMapping("/with-menu")
    public List<RestaurantTo> getAllWithMenu() {
        return super.getAllWithMenu();
    }

    @Override
    @Cacheable(sync = true)
    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> get(@PathVariable int id) {
        return super.get(id);
    }

    @Override
    @Cacheable(sync = true)
    @GetMapping("/{id}/with-menu")
    public ResponseEntity<RestaurantTo> getWithMenu(@PathVariable int id) {
        return super.getWithMenu(id);
    }
}
