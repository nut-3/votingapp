package ru.topjava.votingapp.repository;

import org.springframework.transaction.annotation.Transactional;
import ru.topjava.votingapp.model.LunchMenu;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface LunchMenuRepository extends BaseRepository<LunchMenu> {

    List<LunchMenu> getByRestaurantId(int restaurantId);

    Optional<LunchMenu> getByIdAndRestaurantId(int id, int restaurantId);
}
