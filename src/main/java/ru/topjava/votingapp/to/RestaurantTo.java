package ru.topjava.votingapp.to;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import ru.topjava.votingapp.model.Dish;

import java.util.List;

@Value
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RestaurantTo extends NamedTo {

    @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = NegativeRatingFilter.class)
    Integer rating;

    List<Dish> menu;

    public RestaurantTo(int id, String name, int rating, List<Dish> menu) {
        super(id, name);
        this.rating = rating;
        this.menu = menu;
    }

    static class NegativeRatingFilter {

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Integer rating)) {
                return false;
            }
            return rating < 0;
        }
    }
}
