package com.github.nut3.votingapp.to;

import com.github.nut3.votingapp.model.Dish;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import java.util.List;

@Value
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RestaurantTo extends NamedTo {

    List<Dish> menu;

    public RestaurantTo(int id, String name, List<Dish> menu) {
        super(id, name);
        this.menu = menu;
    }
}
