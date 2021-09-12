package com.github.nut3.votingapp.web.restaurant;

import com.github.nut3.votingapp.AbstractControllerTest;
import com.github.nut3.votingapp.model.Restaurant;
import com.github.nut3.votingapp.model.Vote;
import com.github.nut3.votingapp.repository.VoteRepository;
import com.github.nut3.votingapp.util.RestaurantUtility;
import com.github.nut3.votingapp.web.TestClock10;
import com.github.nut3.votingapp.web.user.UserTestData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.Clock;
import java.time.LocalDate;

import static com.github.nut3.votingapp.web.restaurant.menu.MenuTestData.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
        Restaurant pushkin1 = new Restaurant(RestaurantTestData.pushkin);
        Restaurant mcdonalds1 = new Restaurant(RestaurantTestData.mcdonalds);
        Restaurant kebab1 = new Restaurant(RestaurantTestData.kebab);
        pushkin1.addMenus(pushkinLunchMenu2);
        mcdonalds1.addMenus(mcdonaldsLunchMenu2);
        kebab1.addMenus(kebabLunchMenu2);

        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RestaurantTestData.TO_MATCHER.contentJson(RestaurantUtility.createListTo(pushkin1, mcdonalds1, kebab1)));
    }

    @Test
    void get() throws Exception {
        Restaurant mcdonalds1 = new Restaurant(RestaurantTestData.mcdonalds);
        mcdonalds1.addMenus(mcdonaldsLunchMenu2);

        perform(MockMvcRequestBuilders.get(REST_URL + RestaurantTestData.MCDONALDS_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RestaurantTestData.TO_MATCHER.contentJson(RestaurantUtility.createTo(mcdonalds1)));
    }

    @Test
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + RestaurantTestData.NOT_FOUND))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void vote() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_URL + RestaurantTestData.MCDONALDS_ID + "/vote"))
                .andExpect(status().isNoContent())
                .andDo(print());

        Vote newVote = new Vote(LocalDate.now(clock), UserTestData.user, RestaurantTestData.mcdonalds);
        Vote voteFromRepo = voteRepository.getByUserIdAndDate(UserTestData.USER_ID, LocalDate.now(clock)).orElse(null);
        RestaurantTestData.VOTE_MATCHER.assertMatch(voteFromRepo, newVote);
    }

    @Test
    void voteChange() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_URL + RestaurantTestData.PUSHKIN_ID + "/vote"))
                .andExpect(status().isNoContent())
                .andDo(print());
        perform(MockMvcRequestBuilders.post(REST_URL + RestaurantTestData.KEBAB_ID + "/vote"))
                .andExpect(status().isNoContent())
                .andDo(print());

        Vote newVote = new Vote(LocalDate.now(clock), UserTestData.user, RestaurantTestData.kebab);
        Vote voteFromRepo = voteRepository.getByUserIdAndDate(UserTestData.USER_ID, LocalDate.now(clock)).orElse(null);
        RestaurantTestData.VOTE_MATCHER.assertMatch(voteFromRepo, newVote);
    }
}