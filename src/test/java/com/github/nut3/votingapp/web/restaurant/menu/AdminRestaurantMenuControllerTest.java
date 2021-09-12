package com.github.nut3.votingapp.web.restaurant.menu;

import com.github.nut3.votingapp.AbstractControllerTest;
import com.github.nut3.votingapp.model.LunchMenu;
import com.github.nut3.votingapp.repository.LunchMenuRepository;
import com.github.nut3.votingapp.web.user.UserTestData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.github.nut3.votingapp.util.JsonUtil.writeValue;
import static com.github.nut3.votingapp.web.restaurant.RestaurantTestData.PUSHKIN_ID;
import static com.github.nut3.votingapp.web.restaurant.menu.MenuTestData.MATCHER;
import static com.github.nut3.votingapp.web.restaurant.menu.MenuTestData.getNewMenu;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithUserDetails(value = UserTestData.ADMIN_MAIL)
class AdminRestaurantMenuControllerTest extends AbstractControllerTest {

    private static final String REST_URL = AdminRestaurantMenuController.REST_URL + "/";

    @Autowired
    private LunchMenuRepository lunchMenuRepository;

    @Test
    void addMenu() throws Exception {
        LunchMenu newMenu = getNewMenu();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL + PUSHKIN_ID + "/menus")
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(newMenu)))
                .andDo(print())
                .andExpect(status().isCreated());

        LunchMenu created = MATCHER.readFromJson(action);
        int newId = created.id();
        action.andExpect(redirectedUrlPattern("http*//*" + REST_URL + PUSHKIN_ID + "/menus/" + newId));
        newMenu.setId(newId);
        MATCHER.assertMatch(created, newMenu);
        MATCHER.assertMatch(lunchMenuRepository.getById(newId), newMenu);
    }
}