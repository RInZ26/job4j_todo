package ru.job4j.store;

import ru.job4j.entity.Item;

import java.util.List;
import java.util.Map;

public interface Store {

    void add(Item item);

    boolean replace(Integer id, Map<String, Object> fields);

    boolean delete(Integer id);

    List<Item> findAll();

    List<Item> findByDone(Boolean done);

    Item findById(Integer id);
}
