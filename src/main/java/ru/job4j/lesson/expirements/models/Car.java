package ru.job4j.lesson.expirements.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "cars")
@NoArgsConstructor
@Getter
@Setter
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "engine_id", foreignKey = @ForeignKey(name = "ENGINE_ID_FK"))
    private Engine engine;

    @ManyToMany
    @JoinTable(name = "historyOwners", joinColumns = {
            @JoinColumn(name = "driver_id", nullable = false, updatable = false)},
            inverseJoinColumns = {@JoinColumn(name = "car_id", nullable = false, updatable = false)})
    private Set<Driver> hOwners = new HashSet<>();

    public void addHistoryOwner(Driver historyOwner) {
        this.hOwners.add(historyOwner);
        this.hOwners.forEach(h -> h.getCars().add(this));
    }

    public void rmHistoryOwner(Driver historyOwner) {
        this.hOwners.forEach(h -> h.getCars().remove(this));
        this.hOwners.remove(historyOwner);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Car car = (Car) o;
        return id.equals(car.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
