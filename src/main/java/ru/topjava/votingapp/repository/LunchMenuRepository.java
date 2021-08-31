package ru.topjava.votingapp.repository;

import org.springframework.transaction.annotation.Transactional;
import ru.topjava.votingapp.model.LunchMenu;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface LunchMenuRepository extends BaseRepository<LunchMenu> {

    List<LunchMenu> getAllByRestaurantId(int restaurantId);

    LunchMenu getByRestaurantIdAndDate(int restaurantId, LocalDate date);
}
