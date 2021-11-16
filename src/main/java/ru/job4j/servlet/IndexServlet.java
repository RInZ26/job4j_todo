package ru.job4j.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.job4j.entity.Category;
import ru.job4j.entity.Item;
import ru.job4j.entity.JUser;
import ru.job4j.store.HbmStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;


public class IndexServlet extends HttpServlet {
    private static final Gson GSON = new GsonBuilder().create();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("application/json; charset=utf-8");
        Object byDone = req.getParameter("byDone");
        boolean isUndone = false;
        if (Objects.nonNull(byDone)) {
            isUndone = Boolean.parseBoolean(byDone.toString());
        }
        OutputStream output = resp.getOutputStream();
        Set<Item> items = isUndone ? new LinkedHashSet<>(HbmStore.getInst().findItemsByDone(false))
                : new LinkedHashSet<>(HbmStore.getInst().findAllItems());
        String json = GSON.toJson(items);
        output.write(json.getBytes(StandardCharsets.UTF_8));
        output.flush();
        output.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Item item = Item.of(req.getParameter("description"));

        HttpSession sc = req.getSession();
        JUser user = (JUser) sc.getAttribute("user");
        item.setUser(user);

        String[] cats = req.getParameterValues("cats[]");


        if (Objects.nonNull(cats)) {
            item.setCategories(Arrays.stream(cats).map(c -> new Category(Integer.parseInt(c), null))
                    .collect(Collectors.toSet()));
        }

        HbmStore.getInst().add(item);
        item = HbmStore.getInst().findItem(item.getId());
        resp.setContentType("application/json; charset=utf-8");
        OutputStream output = resp.getOutputStream();
        String json = GSON.toJson(item);
        output.write(json.getBytes(StandardCharsets.UTF_8));
        output.flush();
        output.close();
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Item item = GSON.fromJson(req.getReader(), Item.class);

        HbmStore.getInst().replaceByDone(item.getId(), item.isDone());
    }
}