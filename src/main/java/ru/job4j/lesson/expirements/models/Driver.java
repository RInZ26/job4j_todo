package ru.job4j.lesson.expirements.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "drivers")
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;

    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @Getter
    @Setter
    private Set<Car> cars = new HashSet<>();
}
