package ru.javawebinar.topjavagraduation.model;

import lombok.*;
import org.hibernate.annotations.BatchSize;
import ru.javawebinar.topjavagraduation.HasId;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "restaurants", uniqueConstraints = {@UniqueConstraint(columnNames = "name", name = "restaurants_unique_name_idx")})
public class Restaurant extends NamedEntity implements HasId  {

    @Singular
    @BatchSize(size = 200)
    @OneToMany(fetch = FetchType.LAZY)
    private List<LunchMenu> menus;
}
