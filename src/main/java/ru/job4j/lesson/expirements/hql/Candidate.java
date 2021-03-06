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
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String expirience;

    private long salary;

    @OneToOne(fetch = FetchType.LAZY)
    private VacancyRepo vacancyRepo;

    public static Candidate of(String name, String expirience, long salary) {
        Candidate candidate = new Candidate();
        candidate.setExpirience(expirience);
        candidate.setName(name);
        candidate.setSalary(salary);
        return candidate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Candidate candidate = (Candidate) o;
        return id == candidate.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
