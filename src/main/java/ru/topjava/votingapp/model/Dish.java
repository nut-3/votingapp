package ru.topjava.votingapp.model;

import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
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
    @Range(min = 10)
    private double price;
}