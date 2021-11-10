package ru.job4j.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;


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
        String json = isUndone ? GSON.toJson(HbmStore.getInst().findItemsByDone(false))
                : GSON.toJson(HbmStore.getInst().findAll(Item.class));
        output.write(json.getBytes(StandardCharsets.UTF_8));
        output.flush();
        output.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Item item = GSON.fromJson(req.getReader(), Item.class);
        item.setCreated(Timestamp.valueOf(LocalDateTime.now(ZoneId.of("UTC"))));
        item.setDone(false);

        HttpSession sc = req.getSession();
        JUser user = (JUser) sc.getAttribute("user");
        item.setUser(user);
        HbmStore.getInst().add(item);

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