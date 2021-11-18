package ru.job4j.lesson.expirements.integrationtesting;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class OrdersStoreTest {
    private BasicDataSource pool = new BasicDataSource();

    @Before
    public void setUp()
            throws SQLException {
        pool.setDriverClassName("org.hsqldb.jdbcDriver");
        pool.setUrl("jdbc:hsqldb:mem:tests;sql.syntax_pgs=true");
        pool.setUsername("sa");
        pool.setPassword("");
        pool.setMaxTotal(2);
        StringBuilder builder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream("./db/update_001.sql")))
        ) {
            br.lines().forEach(line -> builder.append(line).append(System.lineSeparator()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        pool.getConnection().prepareStatement(builder.toString()).executeUpdate();
    }

    @After
    public void clean()
            throws SQLException {
        StringBuilder builder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream("./db/delete_001.sql")))
        ) {
            br.lines().forEach(line -> builder.append(line).append(System.lineSeparator()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        pool.getConnection().prepareStatement(builder.toString()).executeUpdate();
    }

    @Test
    public void whenSaveOrderAndFindAllOneRowWithDescription() {
        OrdersStore store = new OrdersStore(pool);

        store.save(Order.of("name1", "description1"));

        List<Order> all = (List<Order>) store.findAll();

        assertThat(all.size(), is(1));
        assertThat(all.get(0).getDescription(), is("description1"));
        assertThat(all.get(0).getId(), is(1));
    }

    @Test
    public void whenUpdateOrderWhichExistThenTrue() {
        OrdersStore store = new OrdersStore(pool);
        Order alina = Order.of("alina", "kind");
        store.save(alina);

        alina = Order.of("my Princess Alina", "my feature");

        Order expected = new Order(1, "my Princess Alina", "my feature",
                alina.getCreated());

        boolean actualBoolResult = store.update(1, alina);

        Order actual = store.findById(1);

        assertTrue(actualBoolResult);
        assertThat(actual, is(expected));
        assertThat(store.findAll().size(), is(1));
    }

    @Test
    public void whenUpdateOrderWhichDoesntExistThenFalseAndDontChangeOther() {
        OrdersStore store = new OrdersStore(pool);
        Order alina = Order.of("alina", "kind");
        store.save(alina);

        alina = Order.of("my Princess Alina", "my feature");

        Order expected = new Order(1, "alina", "kind",
                alina.getCreated());

        boolean actualBoolResult = store.update(2, alina);

        Order actual = store.findById(1);

        assertFalse(actualBoolResult);
        assertThat(actual, is(expected));
        assertThat(store.findAll().size(), is(1));
    }

    @Test
    public void whenFindByNameWith2sameName() {
        OrdersStore store = new OrdersStore(pool);
        Order alina1 = Order.of("alina", "kind");
        Order alina2 = Order.of("alina", "beauty");
        Order anya = Order.of("anya", "nothing");

        store.save(alina1);
        store.save(alina2);
        store.save(anya);

        alina1.setId(1);
        alina2.setId(2);
        anya.setId(3);

        Order[] expected = new Order[]{alina1, alina2};
        Order[] actual = store.findByName("alina").toArray(new Order[2]);

        assertArrayEquals(expected, actual);
    }

    @Test
    public void whenFindByNameWhichDoesntExist() {
        OrdersStore store = new OrdersStore(pool);
        Order alina1 = Order.of("alina", "kind");
        store.save(alina1);

        boolean actual = store.findByName("anya").isEmpty();

        assertTrue(actual);
    }

}