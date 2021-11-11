package ru.job4j.lesson.task.lazyinit;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "car_models")
@AllArgsConstructor
@NoArgsConstructor
@ToString(of={"id", "name"})
public class CarModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private int id;

    @Getter
    @Setter
    private String name;

    public static CarModel of(String name) {
        CarModel carModel = new CarModel();
        carModel.setName(name);
        return carModel;
    }
}
