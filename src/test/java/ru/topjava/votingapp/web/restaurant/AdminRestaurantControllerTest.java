package ru.topjava.votingapp.web.restaurant;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.topjava.votingapp.AbstractControllerTest;
import ru.topjava.votingapp.model.Restaurant;
import ru.topjava.votingapp.repository.RestaurantRepository;
import ru.topjava.votingapp.web.user.UserTestData;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.topjava.votingapp.util.JsonUtil.writeValue;
import static ru.topjava.votingapp.web.restaurant.RestaurantTestData.*;

@WithUserDetails(value = UserTestData.ADMIN_MAIL)
class AdminRestaurantControllerTest extends AbstractControllerTest {

    private static final String REST_URL = AdminRestaurantController.REST_URL + "/";

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + KEBAB_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertFalse(restaurantRepository.findById(KEBAB_ID).isPresent());
    }

    @Test
    void createWithLocation() throws Exception {
        Restaurant newRestaurant = getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(newRestaurant)))
                .andDo(print())
                .andExpect(status().isCreated());

        Restaurant created = MATCHER.readFromJson(action);
        int newId = created.id();
        action.andExpect(redirectedUrlPattern("http*//*" + RestaurantController.REST_URL + "/" + newId));
        newRestaurant.setId(newId);
        MATCHER.assertMatch(created, newRestaurant);
        MATCHER.assertMatch(restaurantRepository.getById(newId), newRestaurant);
    }

    @Test
    void update() throws Exception {
        Restaurant updated = getUpdated();
        updated.setId(null);
        perform(MockMvcRequestBuilders.put(REST_URL + MCDONALDS_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(updated)))
                .andDo(print())
                .andExpect(status().isNoContent());

        MATCHER.assertMatch(restaurantRepository.getById(MCDONALDS_ID), getUpdated());
    }
}