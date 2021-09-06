package ru.topjava.votingapp.web.restaurant;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.topjava.votingapp.AbstractControllerTest;
import ru.topjava.votingapp.web.TestClock12;
import ru.topjava.votingapp.web.user.UserTestData;

import java.time.Clock;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.topjava.votingapp.web.restaurant.RestaurantTestData.MCDONALDS_ID;

@WithUserDetails(value = UserTestData.USER_MAIL)
@Import(TestClock12.class)
public class RestaurantControllerWrongTimeTest extends AbstractControllerTest {

    private static final String REST_URL = RestaurantController.REST_URL + "/";

    @Autowired
    private Clock clock;

    @Test
    void vote() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_URL + MCDONALDS_ID + "/vote"))
                .andExpect(status().isNotAcceptable())
                .andDo(print());
    }
}
