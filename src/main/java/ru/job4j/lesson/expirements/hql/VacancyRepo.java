package ru.job4j.lesson.expirements.hql;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class VacancyRepo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @OneToMany(mappedBy = "vacancyRepo", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Set<Vacancy> vacancies = new HashSet<>();

    public static VacancyRepo of(String name) {
        VacancyRepo vacancyRepo = new VacancyRepo();
        vacancyRepo.setName(name);
        return vacancyRepo;
    }

    public void addVacancy(Vacancy vacancy)
            throws Exception {
        if (vacancy == null) {
            throw new NullPointerException();
        }
        if (vacancy.getVacancyRepo() != null) {
            throw new Exception("VacancyRepo is already defined");
        }

        this.vacancies.add(vacancy);
        vacancy.setVacancyRepo(this);
    }

    public void removeVacancy(Vacancy vacancy) {
        if (vacancy == null) {
            throw new NullPointerException();
        }
        this.vacancies.remove(vacancy);
        vacancy.setVacancyRepo(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        VacancyRepo that = (VacancyRepo) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
