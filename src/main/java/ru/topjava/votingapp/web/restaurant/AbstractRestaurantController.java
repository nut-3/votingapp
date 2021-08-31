package ru.topjava.votingapp.web.restaurant;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import ru.topjava.votingapp.error.TooLateException;
import ru.topjava.votingapp.model.LunchMenu;
import ru.topjava.votingapp.model.Restaurant;
import ru.topjava.votingapp.repository.LunchMenuRepository;
import ru.topjava.votingapp.repository.RestaurantRepository;

import javax.persistence.EntityNotFoundException;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static ru.topjava.votingapp.config.AppConfig.TIME_LIMIT;

@Slf4j
public abstract class AbstractRestaurantController {

    @Autowired
    protected RestaurantRepository restaurantRepository;

    @Autowired
    protected LunchMenuRepository lunchMenuRepository;

    @Autowired
    protected Clock clock;

    public List<Restaurant> getAll() {
        log.info("getAll restaurants");
        return restaurantRepository.getAllWithCurrentLunchMenus(LocalDate.now(clock));
    }

    public ResponseEntity<Restaurant> get(int id) {
        log.info("get restaurant {}", id);
        return ResponseEntity.of(restaurantRepository.findById(id));
    }

    public void delete(int id) {
        log.info("delete restaurant {}", id);
        restaurantRepository.deleteExisted(id);
    }

    public Restaurant save(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    @Transactional
    public LunchMenu addMenu(LunchMenu menu, int restaurantId) {
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(restaurantId);
        if (optionalRestaurant.isEmpty()) {
            throw new EntityNotFoundException("Restaurant with id " + restaurantId + " not found!");
        }
        menu.setRestaurant(optionalRestaurant.get());
        return lunchMenuRepository.save(menu);
    }

    public List<LunchMenu> getMenus(int restaurantId) {
        log.info("get menus for restaurant {}", restaurantId);
        return lunchMenuRepository.getAllByRestaurantId(restaurantId);
    }

    public ResponseEntity<LunchMenu> getMenu(int restaurantId, int id) {
        if (restaurantRepository.findById(restaurantId).isEmpty()) {
            throw new EntityNotFoundException("Restaurant with id " + restaurantId + " not found!");
        }
        log.info("get menu id {} for restaurant {}", id, restaurantId);
        return ResponseEntity.of(lunchMenuRepository.findById(id));
    }

    protected void checkTime() {
        if (LocalTime.now(clock).isAfter(TIME_LIMIT)) {
            throw new TooLateException("Request must be sent before " + TIME_LIMIT.format(DateTimeFormatter.ISO_LOCAL_TIME));
        }
    }
}
