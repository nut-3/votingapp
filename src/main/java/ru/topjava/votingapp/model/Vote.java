package ru.topjava.votingapp.model;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString(callSuper = true)
@Entity
@Table(name = "votes",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "date"}, name = "votes_unique_user_id_date_idx")})
@NamedEntityGraph(
        name = "graph.VoteDetails",
        attributeNodes = {@NamedAttributeNode(value = "user"),
                @NamedAttributeNode(value = "restaurant", subgraph = "subgraph.restaurant")},
        subgraphs = {
                @NamedSubgraph(name = "subgraph.restaurant",
                        attributeNodes = @NamedAttributeNode(value = "menus"))})
public class Vote extends BaseEntity {

    @Column(name = "date", nullable = false, columnDefinition = "date default now()")
    @NotNull
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", foreignKey=@ForeignKey(
            foreignKeyDefinition = "FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE SET NULL"
    ))
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @ToString.Exclude
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ToString.Exclude
    @NotNull
    private Restaurant restaurant;

    public Vote(User user, LocalDate date) {
        this.user = user;
        this.date = date;
    }
}
