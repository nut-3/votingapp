package com.github.nut3.votingapp.web.restaurant;

import com.github.nut3.votingapp.AbstractControllerTest;
import com.github.nut3.votingapp.model.Restaurant;
import com.github.nut3.votingapp.repository.VoteRepository;
import com.github.nut3.votingapp.util.RestaurantUtility;
import com.github.nut3.votingapp.web.user.UserTestData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.github.nut3.votingapp.web.restaurant.menu.MenuTestData.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithUserDetails(value = UserTestData.USER_MAIL)
class RestaurantControllerTest extends AbstractControllerTest {

    private static final String REST_URL = RestaurantController.REST_URL + "/";

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
}