package com.github.nut3.votingapp.util;

import com.github.nut3.votingapp.model.Restaurant;
import com.github.nut3.votingapp.to.RestaurantTo;
import lombok.experimental.UtilityClass;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@UtilityClass
public class RestaurantUtility {

    public static Optional<RestaurantTo> createOptionalTo(Restaurant restaurant) {
        return Optional.ofNullable(createTo(restaurant));
    }

    public static RestaurantTo createTo(Restaurant restaurant) {
        if (restaurant == null) {
            return null;
        }
        return new RestaurantTo(restaurant.id(), restaurant.getName(), restaurant.getMenus().get(0).getDishes());
    }

    public static List<RestaurantTo> createListTo(List<Restaurant> restaurants) {
        if (restaurants == null || restaurants.isEmpty()) {
            return Collections.emptyList();
        }
        return restaurants.stream()
                .map(RestaurantUtility::createTo)
                .collect(Collectors.toList());
    }

    public static List<RestaurantTo> createListTo(Restaurant... restaurants) {
        if (restaurants == null) {
            return Collections.emptyList();
        }
        return createListTo(List.of(restaurants));
    }
}
