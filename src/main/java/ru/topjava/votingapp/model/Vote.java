package ru.topjava.votingapp.model;

import lombok.*;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @ToString.Exclude
    private Restaurant restaurant;

    public Vote(User user, LocalDate date) {
        this.user = user;
        this.date = date;
    }
}
