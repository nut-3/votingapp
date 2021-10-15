package com.github.nut3.votingapp.web.restaurant.menu;

import com.github.nut3.votingapp.AbstractControllerTest;
import com.github.nut3.votingapp.repository.LunchMenuRepository;
import com.github.nut3.votingapp.web.user.UserTestData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static com.github.nut3.votingapp.util.JsonUtil.writeValue;
import static com.github.nut3.votingapp.web.restaurant.RestaurantTestData.MCDONALDS_ID;
import static com.github.nut3.votingapp.web.restaurant.RestaurantTestData.PUSHKIN_ID;
import static com.github.nut3.votingapp.web.restaurant.menu.MenuTestData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithUserDetails(value = UserTestData.USER_MAIL)
class RestaurantMenuControllerTest extends AbstractControllerTest {

    private static final String REST_URL = RestaurantMenuController.REST_URL + "/";

    @Autowired
    private LunchMenuRepository lunchMenuRepository;

    @Test
    void getAll() throws Exception {
        ResultActions action = perform(MockMvcRequestBuilders.get(REST_URL + PUSHKIN_ID + "/menus"))
                .andExpect(status().isOk())
                .andDo(print());
        action.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        assertEquals(writeValue(List.of(pushkinLunchMenu1, pushkinLunchMenu2, pushkinLunchMenu3)),
                action.andReturn().getResponse().getContentAsString());
    }

    @Test
    void get() throws Exception {
        ResultActions action = perform(MockMvcRequestBuilders.get(REST_URL + MCDONALDS_ID + "/menus/" + mcdonaldsLunchMenu2.id()))
                .andExpect(status().isOk())
                .andDo(print());
        action.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        assertEquals(writeValue(mcdonaldsLunchMenu2),
                action.andReturn().getResponse().getContentAsString());
    }
}