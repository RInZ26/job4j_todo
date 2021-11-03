package ru.job4j.store;

import java.io.Closeable;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.entity.Item;

public class HbmStore implements Store, Closeable {

    private static final Store INSTANCE = new HbmStore();

    private final StandardServiceRegistry registry;
    private final SessionFactory sf;

    private HbmStore() {
        this.registry = new StandardServiceRegistryBuilder()
                .configure().build();
        this.sf = new MetadataSources(registry)
                .buildMetadata().buildSessionFactory();
    }

    public static Store getInst() {
        return INSTANCE;
    }

    @Override
    public Item add(Item item) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            session.save(item);
            session.getTransaction().commit();
        }
        return item;
    }

    @Override
    public boolean replace(Integer id, Item item) {
        boolean result;
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            int rowsWereAffected = session.createQuery("UPDATE Item SET description = :description,"
                            + " created = :created, " + "done = :done WHERE id = :id ")
                    .setParameter("id", id)
                    .setParameter("description", item.getDescription())
                    .setParameter("created", item.getCreated())
                    .setParameter("done", item.getDone()).executeUpdate();
            session.getTransaction().commit();

            result = rowsWereAffected > 0;
        }
        return result;
    }

    @Override
    public boolean delete(Integer id) {
        boolean result;
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            int rowsWereAffected = session.createQuery("DELETE from Item WHERE id = :id")
                    .setParameter("id", id).executeUpdate();
            session.getTransaction().commit();

            result = rowsWereAffected > 0;
        }
        return result;
    }

    @Override
    public List<Item> findAll() {
        List<Item> result;
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            result = session.createQuery("from Item order by id").list();
            session.getTransaction().commit();
        }
        return result;
    }

    @Override
    public Item findById(Integer id) {
        Item result;
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            result = session.get(Item.class, id);
            session.getTransaction().commit();
        }
        return result;
    }

    @Override
    public List<Item> findByDone(Boolean done) {
        List<Item> result;
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            result = session.createQuery("from Item where done = :done order by id")
                    .setParameter("done", done).list();
            session.getTransaction().commit();
        }
        return result;
    }

    @Override
    public void close() {
        StandardServiceRegistryBuilder.destroy(registry);
    }
}
