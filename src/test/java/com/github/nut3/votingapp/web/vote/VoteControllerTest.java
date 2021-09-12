package com.github.nut3.votingapp.web.vote;

import com.github.nut3.votingapp.AbstractControllerTest;
import com.github.nut3.votingapp.repository.VoteRepository;
import com.github.nut3.votingapp.to.VoteTo;
import com.github.nut3.votingapp.web.user.UserTestData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static com.github.nut3.votingapp.util.VoteUtil.createListTos;
import static com.github.nut3.votingapp.web.restaurant.RestaurantTestData.pushkin;
import static com.github.nut3.votingapp.web.user.UserTestData.admin;
import static com.github.nut3.votingapp.web.vote.VoteTestData.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithUserDetails(value = UserTestData.USER_MAIL)
class VoteControllerTest extends AbstractControllerTest {

    private static final String REST_URL = VoteController.REST_URL + '/';
    private static final LocalDate minDate = LocalDate.of(1, Month.JANUARY, 1);
    private static final LocalDate maxDate = LocalDate.of(9999, Month.DECEMBER, 31);

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
        List<VoteTo> votesFromRepo = createListTos(voteRepository.getBetweenDates(minDate, maxDate));

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
        List<VoteTo> votesFromRepo = createListTos(voteRepository.getByRestaurantIdAndUserIdBetweenDates(pushkin.id(), admin.id(), LocalDate.of(2021, 8 ,23), LocalDate.of(2021, 8, 30)));
        MATCHER.assertMatch(votes, votesFromRepo);
        MATCHER.assertMatch(votes, votesFromRequest);
    }
}