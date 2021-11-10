package ru.job4j.lesson.task.manyToMany;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HbmRun {


    public static void main(String[] args) {
        addTestGrop();
        rmTestGrop();
    }

    public static void addTestGrop() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            Author alina = Author.of("alina");
            Author anya = Author.of("anya");
            Author nastya = Author.of("nastya");

            Book dissapointment = Book.of("dissapointment");
            Book fear = Book.of("fear");
            Book attention = Book.of("attention");

            alina.addBook(dissapointment);
            alina.addBook(fear);

            anya.addBook(fear);

            nastya.addBook(attention);

            session.save(dissapointment);
            session.save(alina);
            session.save(anya);
            session.save(fear);
            session.save(attention);
            session.save(nastya);

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    /**
     * Удалить Book просто так не выйдет, нужно сначала убрать ее из листа у Author(как в примере)
     * Зато удалить Author можно
     * <p>
     * <p>
     * Book book = session.get(Book.class, 3);
     * Author author = session.get(Author.class, 3);
     * author.getBooks().remove(book);
     * session.remove(book);
     * session.remove(author);
     */
    public static void rmTestGrop() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            Book book = session.get(Book.class, 2);
            Author author1 = session.get(Author.class, 2);
            Author author2 = session.get(Author.class, 1);
            author2.getBooks().remove(book);
            author1.getBooks().remove(book);
            session.remove(book);
            session.remove(author1);
            session.remove(author2);

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}