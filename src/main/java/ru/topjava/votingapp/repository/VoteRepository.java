package ru.topjava.votingapp.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.topjava.votingapp.model.Vote;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {

    Optional<Vote> getByUserIdAndDate(int userId, LocalDate date);

    int countByRestaurantIdAndDate(int restaurantId, LocalDate date);

    
    @Query("select r.id, count(v) from Vote v join v.restaurant r where v.date = :date group by r.id")
    Map<Integer, Integer> countByDateGroupByRestaurantId(LocalDate date);

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
                where v.restaurant.id = :restaurantId
                and m.date = v.date
                and v.date between :start and :end
                order by v.date
            """)
    List<Vote> getByRestaurantIdBetweenDates(int restaurantId, LocalDate start, LocalDate end);

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

    default List<Vote> getVotes(Integer restaurantId, Integer userId, LocalDate start, LocalDate end) {
        if (start == null) {
            // https://docs.oracle.com/javadb/10.6.2.1/ref/rrefdttlimits.html
            start = LocalDate.of(1, Month.JANUARY, 1);
        }
        if (end == null) {
            end = LocalDate.of(9999, Month.DECEMBER, 31);
        }
        if (userId == null && restaurantId == null) {
            return getBetweenDates(start, end);
        } else if (userId == null) {
            return getByRestaurantIdBetweenDates(restaurantId, start, end);
        } else if (restaurantId == null) {
            return getByUserIdBetweenDates(userId, start, end);
        }
        return getByRestaurantIdAndUserIdBetweenDates(restaurantId, userId, start, end);
    }
}
