package com.github.nut3.votingapp.web.vote;

import com.github.nut3.votingapp.MatcherFactory;
import com.github.nut3.votingapp.model.Restaurant;
import com.github.nut3.votingapp.model.Vote;
import com.github.nut3.votingapp.to.VoteTo;
import com.github.nut3.votingapp.web.user.UserTestData;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.util.List;

import static com.github.nut3.votingapp.util.VoteUtil.createTo;
import static com.github.nut3.votingapp.web.restaurant.RestaurantTestData.*;
import static com.github.nut3.votingapp.web.restaurant.menu.MenuTestData.*;

@UtilityClass
public class VoteTestData {
    public static final MatcherFactory.Matcher<VoteTo> MATCHER =
            MatcherFactory.usingIgnoringFieldsComparator(VoteTo.class, "id");

    private static final LocalDate OLD_DATE = LocalDate.of(2021, 8, 22);
    private static final LocalDate NEW_DATE = LocalDate.of(2021, 8, 30);

    public static Vote getAdminVote() {
        Restaurant restaurant = new Restaurant(pushkin);
        restaurant.setMenus(List.of(pushkinLunchMenu2));
        return new Vote(NEW_DATE, UserTestData.admin, restaurant);
    }

    public static Vote getOldAdminVote() {
        Restaurant restaurant = new Restaurant(kebab);
        restaurant.setMenus(List.of(kebabLunchMenu1));
        return new Vote(OLD_DATE, UserTestData.admin, restaurant);
    }

    public static VoteTo getNewVoteTo(LocalDate date) {
        Restaurant restaurant = new Restaurant(mcdonalds);
        restaurant.setMenus(List.of(mcdonaldsLunchMenu2));
        return createTo(new Vote(date, UserTestData.user, restaurant));
    }

    public static VoteTo getUpdatedVoteTo(LocalDate date) {
        Restaurant restaurant = new Restaurant(kebab);
        restaurant.setMenus(List.of(kebabLunchMenu2));
        return createTo(new Vote(date, UserTestData.user, restaurant));
    }
}
