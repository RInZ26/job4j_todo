package ru.job4j.store;

import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import ru.job4j.entity.Item;

import java.io.Closeable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;
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

    /**
     * Замена
     *
     * @param id     - id
     * @param fields мапа полей и значений, чтобы можно обновлять по-отдельности,
     *               название - значение
     * @return
     */
    @Override
    public boolean replace(Integer id, Map<String, Object> fields) {
        StringJoiner sQuery = new StringJoiner("");
        sQuery.add("UPDATE Item ");
        Set<Map.Entry<String, Object>> entrySet = fields.entrySet();
        if (CollectionUtils.isNotEmpty(entrySet)) {
            sQuery.add("SET ");
            for (Map.Entry<String, Object> entry : entrySet) {
                sQuery.add(entry.getKey()).add(" = :").add(entry.getKey());
            }
            sQuery.add(" WHERE id = :id");
        }

        return performTx(s -> {
            Query q = s.createQuery(sQuery.toString());
            q.setParameter("id", id);
            for (Map.Entry<String, Object> entry : entrySet) {
                q.setParameter(entry.getKey(), entry.getValue());
            }
            return q.executeUpdate() > 0;
        });
    }

    @Override
    public boolean delete(Integer id) {
        return this.performTx(s -> s.createQuery("DELETE from Item WHERE id = :id")
                .setParameter("id", id).executeUpdate() > 0);
    }

    @Override
    public List<Item> findAll() {
        return this.<List<Item>>performTx(s -> s.createQuery("from Item order by id").list());
    }

    @Override
    public Item findById(Integer id) {
        return this.performTx(s -> s.get(Item.class, id));
    }

    @Override
    public List<Item> findByDone(Boolean done) {
        return this.<List<Item>>performTx(s -> s.createQuery("from Item where done = :done order by id")
                .setParameter("done", done).list());
    }

    @Override
    public void close() {
        StandardServiceRegistryBuilder.destroy(registry);
    }
}