package ru.job4j.lesson.expirements.hql;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.TimeZone;

public class HbmRun {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf
                    .withOptions()
                    .jdbcTimeZone(TimeZone.getTimeZone("UTC"))
                    .openSession();
            session.beginTransaction();

            Candidate alina = Candidate.of("alina", "20 years", 6666L);
            Candidate lera = Candidate.of("lera", "1 year", 10L);
            Candidate anya = Candidate.of("anya", "infinity", Long.MAX_VALUE);

            session.save(alina);
            session.save(lera);
            session.save(anya);

            Candidate uniqueAlina = (Candidate) session.createQuery("from Candidate c where c.id = :id")
                    .setParameter("id", 1)
                    .uniqueResult();

            Candidate uniqueAnya = (Candidate) session.createQuery("from Candidate c where c.name = :name")
                    .setParameter("name", "anya")
                    .uniqueResult();

            session.createQuery("update Candidate c set c.name ="
                            + "(select concat(cc.name,  'BAD_GIRL') from Candidate cc where cc.id = :idS)" + "  where c.id = :id")
                    .setParameter("id", 3)
                    .setParameter("idS", 2)
                    .executeUpdate();

            lera.setExpirience("Sc2 pro 11 years");

            session.createQuery("delete from Candidate c where c.id = :id")
                    .setParameter("id", 1)
                    .executeUpdate();

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
