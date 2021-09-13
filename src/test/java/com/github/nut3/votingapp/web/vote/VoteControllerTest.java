package com.github.nut3.votingapp.web.vote;

import com.github.nut3.votingapp.AbstractControllerTest;
import com.github.nut3.votingapp.model.Vote;
import com.github.nut3.votingapp.repository.VoteRepository;
import com.github.nut3.votingapp.to.VoteTo;
import com.github.nut3.votingapp.web.TestClock10;
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
import java.time.Month;
import java.util.List;

import static com.github.nut3.votingapp.util.JsonUtil.writeValue;
import static com.github.nut3.votingapp.util.VoteUtil.createListTos;
import static com.github.nut3.votingapp.util.VoteUtil.createTo;
import static com.github.nut3.votingapp.web.restaurant.RestaurantTestData.pushkin;
import static com.github.nut3.votingapp.web.user.UserTestData.ADMIN_ID;
import static com.github.nut3.votingapp.web.vote.VoteTestData.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WithUserDetails(value = UserTestData.USER_MAIL)
@Import(TestClock10.class)
class VoteControllerTest extends AbstractControllerTest {

    @Autowired
    private Clock clock;

    private static final String REST_URL = VoteController.REST_URL + '/';
    private static final LocalDate minDate = LocalDate.of(1, Month.JANUARY, 1);
    private static final LocalDate maxDate = LocalDate.of(9999, Month.DECEMBER, 31);

    @Autowired
    private VoteRepository voteRepository;

    @Test
    @WithUserDetails(value = UserTestData.ADMIN_MAIL)
    void getVotes() throws Exception {
        List<VoteTo> votes = createListTos(List.of(getOldAdminVote(), getAdminVote()));
        ResultActions action = perform(MockMvcRequestBuilders.get(REST_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        List<VoteTo> votesFromRequest = MATCHER.readListFromJson(action);
        List<VoteTo> votesFromRepo = createListTos(voteRepository.getByUserIdBetweenDates(ADMIN_ID, minDate, maxDate));
        MATCHER.assertMatch(votes, votesFromRepo);
        MATCHER.assertMatch(votes, votesFromRequest);
    }

    @Test
    @WithUserDetails(value = UserTestData.ADMIN_MAIL)
    void getVotesForRestaurant() throws Exception {
        List<VoteTo> votes = createListTos(List.of(getAdminVote()));
        ResultActions action = perform(MockMvcRequestBuilders.get(REST_URL + "?restaurantId=" + pushkin.id() + "&from=2021-08-23&to=2021-08-30"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        List<VoteTo> votesFromRequest = MATCHER.readListFromJson(action);
        List<VoteTo> votesFromRepo = createListTos(voteRepository.getByRestaurantIdAndUserIdBetweenDates(pushkin.id(), ADMIN_ID, LocalDate.of(2021, 8, 23), LocalDate.of(2021, 8, 30)));
        MATCHER.assertMatch(votes, votesFromRepo);
        MATCHER.assertMatch(votes, votesFromRequest);
    }


    @Test
    void vote() throws Exception {
        VoteTo newVote = getNewVoteTo(LocalDate.now(clock));
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(newVote)))
                .andDo(print())
                .andExpect(status().isCreated());

        VoteTo created = MATCHER.readFromJson(action);
        int newId = created.id();
        newVote.setId(newId);
        action.andExpect(redirectedUrlPattern("http*//*" + REST_URL + newId));
        Vote voteFromRepo = voteRepository.getByUserIdAndDate(UserTestData.USER_ID, LocalDate.now(clock));
        MATCHER.assertMatch(createTo(voteFromRepo), newVote);
        MATCHER.assertMatch(created, newVote);
    }

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
                .andExpect(status().isNoContent());

        Vote voteFromRepo = voteRepository.getByUserIdAndDate(UserTestData.USER_ID, LocalDate.now(clock));
        MATCHER.assertMatch(createTo(voteFromRepo), updatedVote);
    }
}