package com.github.nut3.votingapp.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString(callSuper = true)
@Embeddable
public class Dish {

    @NotBlank
    @Size(min = 2, max = 100)
    protected String name;

    @Column(nullable = false)
    @Positive
    private int price;
}