package ru.topjava.votingapp.util;

import lombok.experimental.UtilityClass;
import ru.topjava.votingapp.model.Vote;
import ru.topjava.votingapp.to.VoteTo;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class VoteUtil {

    public static VoteTo createTo(Vote vote) {
        return new VoteTo(vote.getDate(), vote.getUser(), RestaurantUtility.createTo(-1, vote.getRestaurant()));
    }

    public static List<VoteTo> createListTos(List<Vote> votes) {
        return votes.stream()
                .map(VoteUtil::createTo)
                .collect(Collectors.toList());
    }
}
