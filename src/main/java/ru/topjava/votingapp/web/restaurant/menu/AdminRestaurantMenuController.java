package ru.topjava.votingapp.web.restaurant.menu;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.topjava.votingapp.model.LunchMenu;

import javax.validation.Valid;
import java.net.URI;

import static ru.topjava.votingapp.util.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = AdminRestaurantMenuController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class AdminRestaurantMenuController extends AbstractRestaurantMenuController {

    static final String REST_URL = "/api/admin/restaurants";

    @PostMapping(value = "/{restaurantId}/menus", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LunchMenu> addMenuWithLocation(@Valid @RequestBody LunchMenu menu, @PathVariable int restaurantId) {
        log.info("add menu for restaurant {}", restaurantId);
        checkNew(menu);
        LunchMenu savedMenu = super.save(menu, restaurantId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{restaurantId}/menus/{id}")
                .buildAndExpand(restaurantId, savedMenu.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(savedMenu);
    }
}
