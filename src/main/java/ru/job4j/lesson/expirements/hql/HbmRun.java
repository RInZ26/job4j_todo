package ru.job4j.lesson.expirements.hql;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.Collection;
import java.util.TimeZone;

public class HbmRun {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();

        Candidate result = null;
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf
                    .withOptions()
                    .jdbcTimeZone(TimeZone.getTimeZone("UTC"))
                    .openSession();
            session.beginTransaction();

            Candidate alina = Candidate.of("alina", "20 years", 6666L);
            Candidate fakeAlina = Candidate.of("alina", "33 years", 7777);

            Vacancy vacancyFirst = Vacancy.of("Java Dev");
            Vacancy vacancySecond = Vacancy.of("Spring Dev");

            VacancyRepo successFullPeople = VacancyRepo.of("success");

            session.save(vacancyFirst);
            session.save(vacancySecond);
            session.save(successFullPeople);
            session.save(alina);
            session.save(fakeAlina);

            alina.setVacancyRepo(successFullPeople);

            successFullPeople.addVacancy(vacancyFirst);
            successFullPeople.addVacancy(vacancySecond);

            result = (Candidate) session.createQuery("from Candidate c join fetch c.vacancyRepo vr "
                            + "join fetch vr.vacancies v where c.id = :id")
                    .setParameter("id", 1).uniqueResult();

            findCandidateByName(session, "alina").forEach(c -> System.out.println(c.getId() + "  "
                    + c.getSalary()));

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
        result.getVacancyRepo().getVacancies().forEach(v -> System.out.println(v.getName()));
    }

    public static Collection<Candidate> findCandidateByName(Session session, String name) {
        return session.createQuery("from Candidate c where c.name = :name")
                .setParameter("name", name).list();
    }
}
