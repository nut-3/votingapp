package ru.topjava.votingapp.web.restaurant;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.topjava.votingapp.AbstractControllerTest;
import ru.topjava.votingapp.model.Restaurant;
import ru.topjava.votingapp.model.Vote;
import ru.topjava.votingapp.repository.VoteRepository;
import ru.topjava.votingapp.web.TestClock10;
import ru.topjava.votingapp.web.user.UserTestData;

import java.time.Clock;
import java.time.LocalDate;
import java.util.Collections;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.topjava.votingapp.util.RestaurantUtility.createListTo;
import static ru.topjava.votingapp.util.RestaurantUtility.createTo;
import static ru.topjava.votingapp.web.restaurant.RestaurantTestData.*;
import static ru.topjava.votingapp.web.restaurant.menu.MenuTestData.*;
import static ru.topjava.votingapp.web.user.UserTestData.USER_ID;
import static ru.topjava.votingapp.web.user.UserTestData.user;

@WithUserDetails(value = UserTestData.USER_MAIL)
@Import(TestClock10.class)
class RestaurantControllerTest extends AbstractControllerTest {

    private static final String REST_URL = RestaurantController.REST_URL + "/";

    @Autowired
    private Clock clock;

    @Autowired
    private VoteRepository voteRepository;


    @Test
    void getAll() throws Exception {
        Restaurant pushkin1 = new Restaurant(pushkin);
        Restaurant mcdonalds1 = new Restaurant(mcdonalds);
        Restaurant kebab1 = new Restaurant(kebab);
        pushkin1.addMenus(pushkinLunchMenu2);
        mcdonalds1.addMenus(mcdonaldsLunchMenu2);
        kebab1.addMenus(kebabLunchMenu2);

        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(TO_MATCHER.contentJson(createListTo(Collections.emptyMap(), pushkin1, mcdonalds1, kebab1)));
    }

    @Test
    void get() throws Exception {
        Restaurant mcdonalds1 = new Restaurant(mcdonalds);
        mcdonalds1.addMenus(mcdonaldsLunchMenu2);

        perform(MockMvcRequestBuilders.get(REST_URL + MCDONALDS_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(TO_MATCHER.contentJson(createTo(0, mcdonalds1)));
    }

    @Test
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + NOT_FOUND))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void vote() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_URL + MCDONALDS_ID + "/vote"))
                .andExpect(status().isNoContent())
                .andDo(print());

        Vote newVote = new Vote(LocalDate.now(clock), user, mcdonalds);
        Vote voteFromRepo = voteRepository.getByUserIdAndDate(USER_ID, LocalDate.now(clock)).orElse(null);
        VOTE_MATCHER.assertMatch(newVote, voteFromRepo);
    }

    @Test
    void voteChange() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_URL + PUSHKIN_ID + "/vote"))
                .andExpect(status().isNoContent())
                .andDo(print());
        perform(MockMvcRequestBuilders.post(REST_URL + KEBAB_ID + "/vote"))
                .andExpect(status().isNoContent())
                .andDo(print());

        Vote newVote = new Vote(LocalDate.now(clock), user, kebab);
        Vote voteFromRepo = voteRepository.getByUserIdAndDate(USER_ID, LocalDate.now(clock)).orElse(null);
        VOTE_MATCHER.assertMatch(newVote, voteFromRepo);
    }
}