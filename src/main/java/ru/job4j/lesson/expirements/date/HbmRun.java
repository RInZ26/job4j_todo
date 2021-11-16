package ru.job4j.lesson.expirements.date;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.Date;

public class HbmRun {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf
                    .withOptions()
                    //  .jdbcTimeZone(TimeZone.getTimeZone("UTC"))
                    .openSession();
            session.beginTransaction();

            Product pr = Product.of("Молоко", "Савушкин продукт");
            session.save(pr);
           Date f = session.find(Product.class, 4).getCreated();
       //     Date s = session.find(Product.class, 2).getCreated();
            System.out.println(f);
          //  System.out.println(s);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}