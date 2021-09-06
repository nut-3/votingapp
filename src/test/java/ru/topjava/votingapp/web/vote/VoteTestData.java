package ru.topjava.votingapp.web.vote;

import lombok.experimental.UtilityClass;
import ru.topjava.votingapp.MatcherFactory;
import ru.topjava.votingapp.model.Restaurant;
import ru.topjava.votingapp.model.Vote;
import ru.topjava.votingapp.to.VoteTo;

import java.time.LocalDate;
import java.util.List;

import static ru.topjava.votingapp.web.restaurant.RestaurantTestData.kebab;
import static ru.topjava.votingapp.web.restaurant.RestaurantTestData.pushkin;
import static ru.topjava.votingapp.web.restaurant.menu.MenuTestData.kebabLunchMenu1;
import static ru.topjava.votingapp.web.restaurant.menu.MenuTestData.pushkinLunchMenu2;
import static ru.topjava.votingapp.web.user.UserTestData.admin;
import static ru.topjava.votingapp.web.user.UserTestData.user;

@UtilityClass
public class VoteTestData {
    public static final MatcherFactory.Matcher<VoteTo> MATCHER =
            MatcherFactory.usingIgnoringFieldsComparator(VoteTo.class, "id", "user.roles", "user.registered", "user.password", "restaurant.rating", "restaurant.menu.menu");

    private static final LocalDate OLD_DATE = LocalDate.of(2021, 8, 22);
    private static final LocalDate NEW_DATE = LocalDate.of(2021, 8, 30);

    public static Vote getAdminVote() {
        Restaurant restaurant = new Restaurant(pushkin);
        restaurant.setMenus(List.of(pushkinLunchMenu2));
        return new Vote(NEW_DATE, admin, restaurant);
    }

    public static Vote getOldAdminVote() {
        Restaurant restaurant = new Restaurant(kebab);
        restaurant.setMenus(List.of(kebabLunchMenu1));
        return new Vote(OLD_DATE, admin, restaurant);
    }

    public static Vote getOldUserVote() {
        Restaurant restaurant = new Restaurant(kebab);
        restaurant.setMenus(List.of(kebabLunchMenu1));
        return new Vote(OLD_DATE, user, restaurant);
    }
}
