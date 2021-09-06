package ru.topjava.votingapp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString(callSuper = true)
@Entity
@Table(name = "lunch_menus", uniqueConstraints = {@UniqueConstraint(columnNames = {"restaurant_id", "date"},
        name = "lunch_menus_unique_restaurant_id_date_idx")})
public class LunchMenu extends BaseEntity {

    @Column(name = "date", nullable = false, columnDefinition = "date default now()")
    @NotNull
    private LocalDate date = LocalDate.now();

    @Size(min = 2, max = 5)
    @BatchSize(size = 200)
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "menu", cascade = CascadeType.ALL)
    @OrderBy("id ASC")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonManagedReference
    private Set<Dish> dishes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @JsonBackReference
    @Hidden
    @ToString.Exclude
    private Restaurant restaurant;

    public LunchMenu(Integer id, LocalDate date) {
        super(id);
        this.date = date;
    }

    public void addDishes(Dish... dishes) {
        if (dishes != null) {
            if (this.dishes == null) {
                this.dishes = new HashSet<>();
            }
            Arrays.stream(dishes).forEach(dish -> dish.setMenu(this));
            this.dishes.addAll(List.of(dishes));
        }
    }
}
