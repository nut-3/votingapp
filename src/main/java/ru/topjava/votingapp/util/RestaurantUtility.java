package ru.topjava.votingapp.util;

import lombok.experimental.UtilityClass;
import ru.topjava.votingapp.model.Restaurant;
import ru.topjava.votingapp.to.RestaurantTo;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@UtilityClass
public class RestaurantUtility {

    public static Optional<RestaurantTo> createOptionalTo(int rating, Restaurant restaurant) {
        return Optional.ofNullable(createTo(rating, restaurant));
    }

    public static RestaurantTo createTo(int rating, Restaurant restaurant) {
        if (restaurant == null) {
            return null;
        }
        return new RestaurantTo(restaurant.id(), restaurant.getName(), rating, restaurant.getMenus().get(0).getDishes());
    }

    public static List<RestaurantTo> createListTo(Map<Integer, Integer> ratings, List<Restaurant> restaurants) {
        if (restaurants == null || restaurants.isEmpty()) {
            return Collections.emptyList();
        }
        return restaurants.stream()
                .map(restaurant -> createTo(ratings.getOrDefault(restaurant.id(), 0), restaurant))
                .collect(Collectors.toList());
    }

    public static List<RestaurantTo> createListTo(Map<Integer, Integer> ratings, Restaurant... restaurants) {
        if (restaurants == null) {
            return Collections.emptyList();
        }
        return createListTo(ratings, List.of(restaurants));
    }
}
