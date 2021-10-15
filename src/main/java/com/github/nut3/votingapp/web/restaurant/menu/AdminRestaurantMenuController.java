package com.github.nut3.votingapp.web.restaurant.menu;

import com.github.nut3.votingapp.model.LunchMenu;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

import static com.github.nut3.votingapp.util.validation.ValidationUtil.assureIdConsistent;
import static com.github.nut3.votingapp.util.validation.ValidationUtil.checkNew;

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

    @PutMapping(value = "/{restaurantId}/menus/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void update(@Valid @RequestBody LunchMenu menu,
                       @PathVariable int restaurantId,
                       @PathVariable int id) {
        log.info("change menu {} for restaurant {}", id, restaurantId);
        assureIdConsistent(menu, id);
        super.save(menu, restaurantId);
    }

    @Override
    @DeleteMapping("/{restaurantId}/menus/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int restaurantId, @PathVariable int id) {
        super.delete(restaurantId, id);
    }
}
