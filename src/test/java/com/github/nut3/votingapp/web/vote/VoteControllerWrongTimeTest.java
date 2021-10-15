package com.github.nut3.votingapp.web.vote;

import com.github.nut3.votingapp.AbstractControllerTest;
import com.github.nut3.votingapp.web.Clocks;
import com.github.nut3.votingapp.web.user.UserTestData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.Clock;

import static com.github.nut3.votingapp.web.restaurant.RestaurantTestData.kebab;
import static com.github.nut3.votingapp.web.restaurant.RestaurantTestData.mcdonalds;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithUserDetails(value = UserTestData.USER_MAIL)
@Import(Clocks.TestClock12.class)
public class VoteControllerWrongTimeTest extends AbstractControllerTest {

    private static final String REST_URL = VoteController.REST_URL + '/';

    @Autowired
    private Clock clock;

    @Test
    void voteChange() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_URL + "?restaurantId=" + mcdonalds.id()))
                .andDo(print())
                .andExpect(status().isCreated());

        perform(MockMvcRequestBuilders.post(REST_URL + "?restaurantId=" + kebab.id()))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }
}
