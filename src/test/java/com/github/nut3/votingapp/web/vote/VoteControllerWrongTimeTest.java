package com.github.nut3.votingapp.web.vote;

import com.github.nut3.votingapp.AbstractControllerTest;
import com.github.nut3.votingapp.to.VoteTo;
import com.github.nut3.votingapp.web.TestClock12;
import com.github.nut3.votingapp.web.user.UserTestData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.Clock;
import java.time.LocalDate;

import static com.github.nut3.votingapp.util.JsonUtil.writeValue;
import static com.github.nut3.votingapp.web.vote.VoteTestData.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithUserDetails(value = UserTestData.USER_MAIL)
@Import(TestClock12.class)
public class VoteControllerWrongTimeTest extends AbstractControllerTest {

    private static final String REST_URL = VoteController.REST_URL + '/';

    @Autowired
    private Clock clock;

    @Test
    void voteChange() throws Exception {
        VoteTo newVote = getNewVoteTo(LocalDate.now(clock));
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(newVote)))
                .andDo(print())
                .andExpect(status().isCreated());
        VoteTo created = MATCHER.readFromJson(action);
        int newId = created.id();

        VoteTo updatedVote = getUpdatedVoteTo(newId, LocalDate.now(clock));

        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(updatedVote)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }
}
