package ru.job4j.store;

import ru.job4j.entity.Item;

import java.util.List;

public interface Store {

    void add(Item item);

    boolean replace(int id, boolean done);

    boolean delete(int id);

    List<Item> findAll();

    List<Item> findByDone(boolean done);

    Item findById(int id);
}
