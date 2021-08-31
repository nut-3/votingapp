package ru.topjava.votingapp.web.restaurant;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.topjava.votingapp.model.LunchMenu;
import ru.topjava.votingapp.model.Restaurant;

import java.util.List;

@RestController
@RequestMapping(value = RestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j()
public class RestaurantController extends AbstractRestaurantController {

    static final String REST_URL = "/api/restaurants";

    @Override
    @GetMapping
    public List<Restaurant> getAll() {
        return super.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> get(@PathVariable int id) {
        return super.get(id);
    }

    @Override
    @GetMapping("/{id}/menus")
    public List<LunchMenu> getMenus(@PathVariable int id) {
        return super.getMenus(id);
    }

    @Override
    @GetMapping("/{restaurantId}/menus/{id}")
    public ResponseEntity<LunchMenu> getMenu(@PathVariable int restaurantId, @PathVariable int id) {
        return super.getMenu(restaurantId, id);
    }

    @PostMapping("/{id}/vote")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void vote(@PathVariable int id) {
        checkTime();
    }
}
