package ru.job4j.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.entity.Item;
import ru.job4j.entity.JUser;

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
     * @param <Y>
     * @return
     */
    public <Y> Y performTx(Function<Session, Y> command) {
        final Session session = sf.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            Y rsl = command.apply(session);
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
    public <T> void add(T entity) {
        this.performTx(s -> s.save(entity));
    }

    @Override
    public<T> T findById(int id, Class clazz) {
        return this.<T>performTx(s -> (T) (s.createQuery("from  " + clazz.getSimpleName()
                        + " where id = :id").setParameter("id", id).uniqueResult()));
    }

    @Override
    public<T> boolean delete(int id, Class clazz) {
        return this.performTx(s -> s.createQuery("DELETE from " + clazz.getSimpleName() + " WHERE id = :id")
                .setParameter("id", id).executeUpdate() > 0);
    }

    @Override
    public <T> List<T> findAll(Class clazz) {
        return this.<List<T>>performTx(s -> s.createQuery("from " + clazz.getSimpleName() + " order by id").list());
    }


    public List<Item> findItemsByDone(boolean done) {
        return this.<List<Item>>performTx(s -> s.createQuery("from Item where done = :done order by id")
                .setParameter("done", done).list());
    }

    @Override
    public boolean replaceByDone(int id, boolean done) {
        return this.performTx(s ->
                s.createQuery("UPDATE Item SET done = :done WHERE id = :id")
                        .setParameter("id", id).setParameter("done", done)
                        .executeUpdate() > 0);
    }

    @Override
    public JUser findUserByEmail(String email) {
        return this.<JUser>performTx(s -> (JUser) s.createQuery("from JUser where email = :email")
                .setParameter("email", email).uniqueResult());
    }

    @Override
    public void close() {
        StandardServiceRegistryBuilder.destroy(registry);
    }
}