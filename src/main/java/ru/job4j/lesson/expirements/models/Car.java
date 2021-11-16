package ru.job4j.lesson.expirements.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "cars")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;

    @OneToOne
    @Getter
    @Setter
    private Engine engine;

    @ManyToMany(mappedBy = "cars")
    @Getter
    @Setter
    private Set<HistoryOwner> hOwners = new HashSet<>();

    private void addHistoryOwner(HistoryOwner hO) {
        this.hOwners.add(hO);
        this.hOwners.forEach(h -> h.getCars().add(this));
    }

    private void rmHistoryOwner(HistoryOwner hO) {
        this.hOwners.forEach(h -> h.getCars().remove(this));
        this.hOwners.remove(hO);
    }
}
