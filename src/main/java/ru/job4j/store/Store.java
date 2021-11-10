package ru.job4j.store;

import ru.job4j.entity.Item;
import ru.job4j.entity.JUser;

import java.util.List;

public interface Store {

    <T> void add(T entity);

    <T> T findById(int id, Class clazz);

    <T> boolean delete(int id, Class clazz);

    <T> List<T> findAll(Class clazz);

    boolean replaceByDone(int id, boolean done);

    List<Item> findItemsByDone(boolean done);

    JUser findUserByEmail(String email);
}
