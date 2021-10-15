package com.github.nut3.votingapp.to;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import java.time.LocalDate;

@Value
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class VoteTo extends BaseTo {

    LocalDate date;

    String restaurantName;

    public VoteTo(Integer id, LocalDate date, String restaurantName) {
        super(id);
        this.date = date;
        this.restaurantName = restaurantName;
    }
}
