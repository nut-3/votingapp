package ru.topjava.votingapp.web.restaurant;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.topjava.votingapp.AbstractControllerTest;
import ru.topjava.votingapp.model.LunchMenu;
import ru.topjava.votingapp.model.Restaurant;
import ru.topjava.votingapp.repository.LunchMenuRepository;
import ru.topjava.votingapp.repository.RestaurantRepository;
import ru.topjava.votingapp.web.user.UserTestData;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.topjava.votingapp.util.JsonUtil.writeValue;
import static ru.topjava.votingapp.web.restaurant.RestaurantTestData.*;

@WithUserDetails(value = UserTestData.ADMIN_MAIL)
class AdminRestaurantControllerTest extends AbstractControllerTest {

    private static final String REST_URL = AdminRestaurantController.REST_URL + "/";

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private LunchMenuRepository lunchMenuRepository;

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + KEBAB_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertFalse(restaurantRepository.findById(KEBAB_ID).isPresent());
    }

    @Test
    void createWithLocation() throws Exception {
        Restaurant newRestaurant = getNewRestaurant();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(newRestaurant)))
                .andDo(print())
                .andExpect(status().isCreated());

        Restaurant created = MATCHER.readFromJson(action);
        int newId = created.id();
        newRestaurant.setId(newId);
        MATCHER.assertMatch(created, newRestaurant);
        MATCHER.assertMatch(restaurantRepository.getById(newId), newRestaurant);
    }

    @Test
    void addMenuWithLocation() throws Exception {
        LunchMenu newMenu = getNewMenu();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL + PUSHKIN_ID + "/menus")
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(newMenu)))
                .andDo(print())
                .andExpect(status().isCreated());

        LunchMenu addedMenu = MENU_MATCHER.readFromJson(action);
        LunchMenu addedFromRepository = lunchMenuRepository.getByRestaurantIdAndDate(PUSHKIN_ID, newMenu.getDate());
        MENU_MATCHER.assertMatch(addedMenu, newMenu);
        MENU_MATCHER.assertMatch(addedFromRepository, newMenu);
    }

    @Slf4j
    @TestConfiguration
    static class TestClock {

        @Primary
        @Bean
        public Clock fixedClockForAdminController() {
            Clock clock = Clock.fixed(Instant.parse("2021-08-30T10:00:00.00Z"), ZoneId.of("UTC"));
            log.info("Setting time to {}", LocalDateTime.now(clock));
            return clock;
        }
    }
}