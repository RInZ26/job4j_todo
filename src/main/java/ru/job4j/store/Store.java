package ru.job4j.store;

import ru.job4j.entity.Item;

import java.util.List;

public interface Store {
    Item add(Item item);

    boolean replace(Integer id, Item item);

    boolean delete(Integer id);

    List<Item> findAll();

    List<Item> findByDone(Boolean done);

    Item findById(Integer id);
}
