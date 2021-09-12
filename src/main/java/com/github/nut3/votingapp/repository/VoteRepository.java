package com.github.nut3.votingapp.repository;

import com.github.nut3.votingapp.model.Vote;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {

    Vote getByUserIdAndDate(int userId, LocalDate date);

    @EntityGraph(value = "graph.VoteDetails", type = EntityGraph.EntityGraphType.LOAD)
    @Query("""
            select v from Vote v left join v.restaurant r left join r.menus m
                where v.restaurant.id = :restaurantId
                and m.date = v.date
                and v.user.id = :userId
                and v.date between :start and :end
                order by v.date
            """)
    List<Vote> getByRestaurantIdAndUserIdBetweenDates(int restaurantId, int userId, LocalDate start, LocalDate end);

    @EntityGraph(value = "graph.VoteDetails", type = EntityGraph.EntityGraphType.LOAD)
    @Query("""
            select v from Vote v left join v.restaurant r left join r.menus m
                where v.user.id = :userId
                and m.date = v.date
                and v.date between :start and :end
                order by v.date
            """)
    List<Vote> getByUserIdBetweenDates(int userId, LocalDate start, LocalDate end);

    @EntityGraph(value = "graph.VoteDetails", type = EntityGraph.EntityGraphType.LOAD)
    @Query("""
            select v from Vote v left join v.restaurant r left join r.menus m
                where v.date between :start and :end
                and m.date = v.date
                order by v.date
            """)
    List<Vote> getBetweenDates(LocalDate start, LocalDate end);
}
