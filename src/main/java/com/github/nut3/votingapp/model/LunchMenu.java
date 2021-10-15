package com.github.nut3.votingapp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Setter
@Getter
@ToString(callSuper = true)
@Entity
@Table(name = "lunch_menus", uniqueConstraints = {@UniqueConstraint(columnNames = {"restaurant_id", "curr_date"},
        name = "lunch_menus_unique_restaurant_id_curr_date_idx")})
public class LunchMenu extends BaseEntity {

    @Column(name = "curr_date", nullable = false, columnDefinition = "date default now()")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDate date;

    @Size(min = 2, max = 5)
    @BatchSize(size = 200)
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "dishes", joinColumns = @JoinColumn(name = "menu_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"menu_id", "name"}, name = "dishes_unique_menu_id_name_idx")})
    @JoinColumn(name = "menu_id")
    @Column(name = "name")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Dish> dishes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @JsonBackReference
    @ToString.Exclude
    private Restaurant restaurant;

    public LunchMenu(Integer id, LocalDate date) {
        super(id);
        this.date = date;
    }

    public LunchMenu(LunchMenu m) {
        this(m.id, m.date);
    }

    public void addDishes(Dish... dishes) {
        if (dishes != null) {
            if (this.dishes == null) {
                this.dishes = new ArrayList<>();
            }
            this.dishes.addAll(List.of(dishes));
        }
    }

    public void setDishes(Dish... dishes) {
        if (this.dishes != null) {
            this.dishes.clear();
        }
        addDishes(dishes);
    }
}
