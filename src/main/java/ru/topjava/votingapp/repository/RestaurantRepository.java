package ru.topjava.votingapp.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.topjava.votingapp.model.Restaurant;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant> {

    @EntityGraph(attributePaths = {"menus"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("select r from Restaurant r where r.id = :id")
    Restaurant getWithLunchMenus(@Param("id") int id);

    @EntityGraph(attributePaths = {"menus"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("select r from Restaurant r")
    List<Restaurant> getAllWithLunchMenus();

    @EntityGraph(attributePaths = {"menus"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("select r from Restaurant r join r.menus as m where m.date = :date")
    List<Restaurant> getAllWithCurrentLunchMenus(LocalDate date);
}
