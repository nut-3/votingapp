package com.github.nut3.votingapp.web.restaurant.menu;

import com.github.nut3.votingapp.AbstractControllerTest;
import com.github.nut3.votingapp.model.LunchMenu;
import com.github.nut3.votingapp.repository.LunchMenuRepository;
import com.github.nut3.votingapp.web.Clocks;
import com.github.nut3.votingapp.web.user.UserTestData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.github.nut3.votingapp.util.JsonUtil.writeValue;
import static com.github.nut3.votingapp.web.restaurant.RestaurantTestData.PUSHKIN_ID;
import static com.github.nut3.votingapp.web.restaurant.menu.MenuTestData.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(Clocks.TestClockForMenu.class)
@WithUserDetails(value = UserTestData.ADMIN_MAIL)
class AdminRestaurantMenuControllerTest extends AbstractControllerTest {

    private static final String REST_URL = AdminRestaurantMenuController.REST_URL + "/";

    @Autowired
    private LunchMenuRepository lunchMenuRepository;

    @Test
    void addMenu() throws Exception {
        LunchMenu newMenu = getNew();
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

    @Test
    void update() throws Exception {
        LunchMenu updatedMenu = getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL + PUSHKIN_ID + "/menus/" + updatedMenu.id())
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(updatedMenu)))
                .andDo(print())
                .andExpect(status().isNoContent());

        MATCHER.assertMatch(lunchMenuRepository.getById(updatedMenu.id()), updatedMenu);
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + PUSHKIN_ID + "/menus/" + pushkinLunchMenu1.id()))
                .andDo(print())
                .andExpect(status().isNoContent());

        assertFalse(lunchMenuRepository.findById(pushkinLunchMenu1.id()).isPresent());
    }
}