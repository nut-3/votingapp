package com.github.nut3.votingapp.repository;

import com.github.nut3.votingapp.model.Restaurant;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant> {

    @EntityGraph(attributePaths = {"menus"}, type = EntityGraph.EntityGraphType.LOAD)
    Restaurant getByIdAndMenusDate(int id, LocalDate date);

    @EntityGraph(attributePaths = {"menus"}, type = EntityGraph.EntityGraphType.LOAD)
    List<Restaurant> getAllByMenusDate(LocalDate date);
}
