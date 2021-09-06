package ru.topjava.votingapp.to;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import ru.topjava.votingapp.model.User;

import java.time.LocalDate;

@Value
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class VoteTo extends BaseTo {

    LocalDate date;

    User user;

    RestaurantTo restaurant;
}
