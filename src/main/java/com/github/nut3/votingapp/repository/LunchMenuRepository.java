package com.github.nut3.votingapp.repository;

import com.github.nut3.votingapp.model.LunchMenu;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface LunchMenuRepository extends BaseRepository<LunchMenu> {

    List<LunchMenu> getByRestaurantId(int restaurantId);

    Optional<LunchMenu> getByIdAndRestaurantId(int id, int restaurantId);
}
