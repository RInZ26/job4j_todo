package ru.job4j.lesson.task.onetomany;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.ArrayList;
import java.util.List;

public class HbmRun {

    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            CarModel first = CarModel.builder().name("alina").build();
            CarModel second = CarModel.builder().name("nastya").build();
            CarModel third = CarModel.builder().name("anya").build();
            CarModel fourth = CarModel.builder().name("goddess").build();
            CarModel fifth = CarModel.builder().name("princess").build();

            List<CarModel> models = new ArrayList<>(List.of(first, second,
                    third, fourth, fifth));

            models.forEach(session::save);

            CarBrand brand = CarBrand.builder().name("prettyGirls").carModels(models).build();

            session.save(brand);

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}