package ru.topjava.votingapp.web.vote;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.topjava.votingapp.AbstractControllerTest;
import ru.topjava.votingapp.repository.VoteRepository;
import ru.topjava.votingapp.to.VoteTo;
import ru.topjava.votingapp.web.user.UserTestData;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.topjava.votingapp.util.VoteUtil.createListTos;
import static ru.topjava.votingapp.web.restaurant.RestaurantTestData.pushkin;
import static ru.topjava.votingapp.web.vote.VoteTestData.*;

@WithUserDetails(value = UserTestData.USER_MAIL)
class VoteControllerTest extends AbstractControllerTest {

    private static final String REST_URL = VoteController.REST_URL + '/';

    @Autowired
    private VoteRepository voteRepository;

    @Test
    void getVotes() throws Exception {
        List<VoteTo> votes = createListTos(List.of(getOldUserVote(), getOldAdminVote(), getAdminVote()));

        ResultActions action = perform(MockMvcRequestBuilders.get(REST_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        List<VoteTo> votesFromRequest = MATCHER.readArrayFromJson(action);
        List<VoteTo> votesFromRepo = createListTos(voteRepository.getVotes(null, null, null, null));

        MATCHER.assertMatch(votes, votesFromRepo);
        MATCHER.assertMatch(votes, votesFromRequest);
    }

    @Test
    void getVotesForAdmin() throws Exception {
        List<VoteTo> votes = createListTos(List.of(getAdminVote()));
        ResultActions action = perform(MockMvcRequestBuilders.get(REST_URL + "?restaurantId=" + pushkin.id() + "&from=2021-08-23&to=2021-08-30"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        List<VoteTo> votesFromRequest = MATCHER.readArrayFromJson(action);
        List<VoteTo> votesFromRepo = createListTos(voteRepository.getVotes(pushkin.id(), null, LocalDate.of(2021, 8 ,23), LocalDate.of(2021, 8, 30)));
        MATCHER.assertMatch(votes, votesFromRepo);
        MATCHER.assertMatch(votes, votesFromRequest);
    }
}