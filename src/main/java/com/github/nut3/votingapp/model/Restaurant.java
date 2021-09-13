package com.github.nut3.votingapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@NoArgsConstructor
@Setter
@Getter
@ToString(callSuper = true)
@Entity
@Table(name = "restaurants", uniqueConstraints = {@UniqueConstraint(columnNames = "name", name = "restaurants_unique_name_idx")})
public class Restaurant extends NamedEntity {

    @BatchSize(size = 200)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @OnDelete(action = OnDeleteAction.CASCADE) //https://stackoverflow.com/a/44988100/548473
    @JsonIgnore
    @ToString.Exclude
    @OrderBy("date DESC")
    private List<LunchMenu> menus;

    public Restaurant(Integer id, String name) {
        super(id, name);
    }

    public Restaurant(Restaurant r) {
        super(r.id, r.name);
        menus = r.menus;
    }

    public void addMenus(LunchMenu... menus) {
        if (menus != null) {
            if (this.menus == null) {
                this.menus = new ArrayList<>();
            }
            Arrays.asList(menus).forEach(menu -> {
                menu.setRestaurant(this);
                this.menus.add(menu);
            });
        }
    }
}
