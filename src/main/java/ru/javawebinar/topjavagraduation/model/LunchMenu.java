package ru.javawebinar.topjavagraduation.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.javawebinar.topjavagraduation.HasId;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "lunch_menus", uniqueConstraints = {@UniqueConstraint(columnNames = {"restaurant_id", "date"},
        name = "lunch_menus_unique_restaurant_id_date_idx")})
public class LunchMenu extends BaseEntity  implements HasId {

    @Column(name = "date", nullable = false, columnDefinition = "date default now()")
    @NotNull
    private final Date date = new Date();

    @Singular
    @BatchSize(size = 200)
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "menu")
    @OrderBy("id ASC ")
    private Set<Dish> dishes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    @NotNull
    private Restaurant restaurant;
}
