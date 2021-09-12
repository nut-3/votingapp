package com.github.nut3.votingapp.web.restaurant;

import com.github.nut3.votingapp.MatcherFactory;
import com.github.nut3.votingapp.model.Restaurant;
import com.github.nut3.votingapp.to.RestaurantTo;
import lombok.experimental.UtilityClass;

@UtilityClass
public class RestaurantTestData {
    public static final MatcherFactory.Matcher<Restaurant> MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class, "menu");
    public static final MatcherFactory.Matcher<RestaurantTo> TO_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(RestaurantTo.class, "menu.id", "menu.menu");

    public static final int PUSHKIN_ID = 1;
    public static final int MCDONALDS_ID = 2;
    public static final int KEBAB_ID = 3;
    public static final int NOT_FOUND = 100;

    public static final Restaurant pushkin = new Restaurant(PUSHKIN_ID, "Pushkin");
    public static final Restaurant mcdonalds = new Restaurant(MCDONALDS_ID, "McDonald's");
    public static final Restaurant kebab = new Restaurant(KEBAB_ID, "Doner Kebab");

    public static Restaurant getNew() {
        return new Restaurant(null, "New test restaurant");
    }

    public static Restaurant getUpdated() {
        Restaurant restaurant = new Restaurant(mcdonalds);
        restaurant.setName("Updated McDonald's");
        return restaurant;
    }
}
