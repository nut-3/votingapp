package com.github.nut3.votingapp.util;

import com.github.nut3.votingapp.model.Vote;
import com.github.nut3.votingapp.to.VoteTo;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class VoteUtil {

    public static VoteTo createTo(Vote vote) {
        return new VoteTo(vote.getDate(), RestaurantUtility.createTo(vote.getRestaurant()));
    }

    public static List<VoteTo> createListTos(List<Vote> votes) {
        return votes.stream()
                .map(VoteUtil::createTo)
                .collect(Collectors.toList());
    }
}
