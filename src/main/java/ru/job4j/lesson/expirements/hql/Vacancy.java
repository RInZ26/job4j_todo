package ru.job4j.lesson.expirements.hql;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Vacancy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @ManyToOne
    private VacancyRepo vacancyRepo;

    public static Vacancy of(String name) {
        Vacancy vacancy = new Vacancy();
        vacancy.setName(name);
        return vacancy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Vacancy vacancy = (Vacancy) o;
        return id == vacancy.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
