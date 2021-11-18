package ru.job4j.lesson.task.lazyinit;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.ArrayList;
import java.util.List;

public class HbmRun {

    public static void main(String[] args) {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            CarModel first = CarModel.of("alina");
            CarModel second = CarModel.of("nastya");
            CarModel third = CarModel.of("anya");
            CarModel fourth = CarModel.of("goddess");
            CarModel fifth = CarModel.of("princess");

            List<CarModel> models = new ArrayList<>(List.of(first, second,
                    third, fourth, fifth));

            CarBrand brand = CarBrand.of("prettyGirls");
            brand.setCarModels(models);

            session.save(brand);

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }

        registry = new StandardServiceRegistryBuilder()
                .configure().build();

        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            List<CarBrand> carBrand = session.createQuery("select cb "
                    + "from CarBrand cb join fetch cb.carModels").list();
            session.getTransaction().commit();
            session.close();

            carBrand.forEach(cb -> cb.getCarModels().forEach(System.out::println));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}