package ru.job4j.lesson.expirements.models;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HbmRun {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf
                    .withOptions()
                    .openSession();
            session.beginTransaction();

            Car car = new Car();
            Engine engine = new Engine();
            Driver historyOwner = new Driver();
            session.save(historyOwner);

            Driver driver = new Driver();
            car.addHistoryOwner(historyOwner);
            session.save(car);
            session.save(engine);

            session.save(driver);

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
