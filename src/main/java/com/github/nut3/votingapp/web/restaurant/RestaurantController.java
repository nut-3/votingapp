package com.github.nut3.votingapp.web.restaurant;

import com.github.nut3.votingapp.to.RestaurantTo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = RestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j()
public class RestaurantController extends AbstractRestaurantController {

    static final String REST_URL = "/api/restaurants";

    @Override
    @GetMapping
    public List<RestaurantTo> getAll() {
        return super.getAll();
    }

    @Override
    @GetMapping("/with-menu")
    public List<RestaurantTo> getAllWithMenu() {
        return super.getAllWithMenu();
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<RestaurantTo> get(@PathVariable int id) {
        return super.get(id);
    }

    @Override
    @GetMapping("/{id}/with-menu")
    public ResponseEntity<RestaurantTo> getWithMenu(@PathVariable int id) {
        return super.getWithMenu(id);
    }
}
