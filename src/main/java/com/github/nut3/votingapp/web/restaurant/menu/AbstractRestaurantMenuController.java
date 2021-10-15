package com.github.nut3.votingapp.web.restaurant.menu;

import com.github.nut3.votingapp.model.LunchMenu;
import com.github.nut3.votingapp.model.Restaurant;
import com.github.nut3.votingapp.repository.LunchMenuRepository;
import com.github.nut3.votingapp.repository.RestaurantRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDate;
import java.util.List;

@Slf4j
public abstract class AbstractRestaurantMenuController {

    @Autowired
    private LunchMenuRepository lunchMenuRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    protected Clock clock;

    @Transactional
    public LunchMenu save(LunchMenu menu, int restaurantId) {
        Restaurant restaurant = restaurantRepository.getById(restaurantId);
        if (!menu.isNew()) {
            LunchMenu storedMenu = lunchMenuRepository.getById(menu.id());
            menu.setDate(storedMenu.getDate());
        } else {
            menu.setDate(LocalDate.now(clock));
        }
        menu.setRestaurant(restaurant);
        return lunchMenuRepository.save(menu);
    }

    public List<LunchMenu> getAll(int id) {
        log.info("get menus for restaurant {}", id);
        return lunchMenuRepository.getByRestaurantId(id);
    }

    public ResponseEntity<LunchMenu> get(int id, int restaurantId) {
        log.info("get menu {} for restaurant {}", id, restaurantId);
        return ResponseEntity.of(lunchMenuRepository.getByIdAndRestaurantId(id, restaurantId));
    }

    public void delete(int restaurantId, int id) {
        log.info("delete menu {} for restaurant {}", id, restaurantId);
        lunchMenuRepository.delete(id);
    }
}
