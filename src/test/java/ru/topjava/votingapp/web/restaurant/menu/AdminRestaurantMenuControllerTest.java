package ru.topjava.votingapp.web.restaurant.menu;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.topjava.votingapp.AbstractControllerTest;
import ru.topjava.votingapp.model.LunchMenu;
import ru.topjava.votingapp.repository.LunchMenuRepository;
import ru.topjava.votingapp.web.user.UserTestData;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.topjava.votingapp.util.JsonUtil.writeValue;
import static ru.topjava.votingapp.web.restaurant.RestaurantTestData.PUSHKIN_ID;
import static ru.topjava.votingapp.web.restaurant.menu.MenuTestData.MATCHER;
import static ru.topjava.votingapp.web.restaurant.menu.MenuTestData.getNewMenu;

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