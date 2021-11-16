package ru.job4j.lesson.expirements.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "hOwners")
public class HistoryOwner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @Getter
    @Setter
    private Set<Car> cars = new HashSet<>();

    private void addCar(Car car) {
        this.cars.add(car);
        this.cars.forEach(h -> h.getHOwners().add(this));
    }

    private void rmCar(Car car) {
        this.cars.forEach(h -> h.getHOwners().remove(this));
        this.cars.remove(car);
    }
}
