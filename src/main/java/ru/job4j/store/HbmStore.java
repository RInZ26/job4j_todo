package ru.job4j.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.entity.Item;

import java.io.Closeable;
import java.util.List;
import java.util.function.Function;

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

    /**
     * Общий метод для всех транзакций через wrapper
     *
     * @param command
     * @param <T>
     * @return
     */
    public <T> T performTx(Function<Session, T> command) {
        final Session session = sf.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            T rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public void add(Item item) {
        this.performTx(s -> s.save(item));
    }

    @Override
    public boolean replace(int id, boolean done) {
        return performTx(s ->
            s.createQuery("UPDATE Item SET done = :done WHERE id = :id")
                    .setParameter("id", id).setParameter("done", done)
                    .executeUpdate() > 0);
    }

    @Override
    public boolean delete(int id) {
        return this.performTx(s -> s.createQuery("DELETE from Item WHERE id = :id")
                .setParameter("id", id).executeUpdate() > 0);
    }

    @Override
    public List<Item> findAll() {
        return this.<List<Item>>performTx(s -> s.createQuery("from Item order by id").list());
    }

    @Override
    public Item findById(int id) {
        return this.performTx(s -> s.get(Item.class, id));
    }

    @Override
    public List<Item> findByDone(boolean done) {
        return this.<List<Item>>performTx(s -> s.createQuery("from Item where done = :done order by id")
                .setParameter("done", done).list());
    }

    @Override
    public void close() {
        StandardServiceRegistryBuilder.destroy(registry);
    }
}