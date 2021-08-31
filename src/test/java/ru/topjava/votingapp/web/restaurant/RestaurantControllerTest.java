package ru.topjava.votingapp.web.restaurant;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.topjava.votingapp.AbstractControllerTest;
import ru.topjava.votingapp.model.Restaurant;
import ru.topjava.votingapp.web.user.UserTestData;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.topjava.votingapp.web.restaurant.RestaurantTestData.*;

@WithUserDetails(value = UserTestData.USER_MAIL)
class RestaurantControllerTest extends AbstractControllerTest {

    private static final String REST_URL = RestaurantController.REST_URL + "/";

    @Test
    void getAll() throws Exception {
        Restaurant pushkin1 = new Restaurant(pushkin);
        pushkin1.setMenus(List.of(pushkinLunchMenu2));
        Restaurant mcdonalds1 = new Restaurant(mcdonalds);
        mcdonalds1.setMenus(List.of(mcdonaldsLunchMenu2));
        Restaurant kebab1 = new Restaurant(kebab);
        kebab1.setMenus(List.of(kebabLunchMenu2));

        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentJson(pushkin1, mcdonalds1, kebab1));
    }

    @Test
    void get() throws Exception {
        Restaurant mcdonalds1 = new Restaurant(mcdonalds);
        mcdonalds1.setMenus(List.of(mcdonaldsLunchMenu1, mcdonaldsLunchMenu2));

        perform(MockMvcRequestBuilders.get(REST_URL + MCDONALDS_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentJson(mcdonalds));
    }

    @Test
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + NOT_FOUND))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void getMenus() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + PUSHKIN_ID + "/menus"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_MATCHER.contentJson(List.of(pushkinLunchMenu1, pushkinLunchMenu2)));
    }

    @Test
    void getMenu() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + PUSHKIN_ID + "/menus/" + pushkinLunchMenu2.id()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_MATCHER.contentJson(pushkinLunchMenu2));
    }

    @Slf4j
    @TestConfiguration
    static class TestClock {

        @Primary
        @Bean
        public Clock fixedClockForRestaurantController() {
            Clock clock = Clock.fixed(Instant.parse("2021-08-30T10:00:00.00Z"), ZoneId.of("UTC"));
            log.info("Setting time to {}", LocalDateTime.now(clock));
            return clock;
        }
    }
}