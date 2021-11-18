package ru.job4j.lesson.task.lazyinit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carBrands")
@AllArgsConstructor
@NoArgsConstructor
public class CarBrand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private int id;

    @Getter
    @Setter
    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @Getter
    @Setter
    private List<CarModel> carModels = new ArrayList<>();

    public static CarBrand of(String name) {
        CarBrand carBrand = new CarBrand();
        carBrand.setName(name);
        return carBrand;
    }

    public void addCarModel(CarModel carModel) {
        this.carModels.add(carModel);
    }
}
