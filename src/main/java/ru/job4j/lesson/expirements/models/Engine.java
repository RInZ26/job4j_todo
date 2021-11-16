package ru.job4j.lesson.expirements.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "engines")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
public class Engine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;

    @OneToOne(mappedBy = "engine")
    @Getter
    @Setter
    private Car car;
}
