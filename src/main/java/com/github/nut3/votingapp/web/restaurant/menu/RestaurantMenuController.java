package com.github.nut3.votingapp.web.restaurant.menu;

import com.github.nut3.votingapp.model.LunchMenu;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = RestaurantMenuController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j()
public class RestaurantMenuController extends AbstractRestaurantMenuController {

    static final String REST_URL = "/api/restaurants";

    @Override
    @GetMapping("/{restaurantId}/menus")
    public List<LunchMenu> getAll(@PathVariable int restaurantId) {
        return super.getAll(restaurantId);
    }

    @Override
    @GetMapping("/{restaurantId}/menus/{id}")
    public ResponseEntity<LunchMenu> get(@PathVariable int id, @PathVariable int restaurantId) {
        return super.get(id, restaurantId);
    }
}
