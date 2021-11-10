package ru.job4j.lesson.task.oneToMany;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "c_brand")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarBrand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    int id;

    @Getter
    @Setter
    String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @Getter
    @Setter
    List<CarModel> carModels = new ArrayList<>();

    public void addCarModel(CarModel carModel) {
        this.carModels.add(carModel);
    }
}
