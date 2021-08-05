package ru.javawebinar.topjavagraduation.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Range;
import ru.javawebinar.topjavagraduation.HasId;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Table(name = "dishes",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"menu_id", "name"}, name = "users_unique_email_idx")})
public class Dish extends NamedEntity implements HasId {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    @NotNull
    private LunchMenu menu;

    @Column(nullable = false)
    @Range(min = 10)
    private int price;
}