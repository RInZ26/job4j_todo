package ru.job4j.lesson.task.oneToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "c_brand")
@AllArgsConstructor
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
    List<CarModel> carModels;
}
