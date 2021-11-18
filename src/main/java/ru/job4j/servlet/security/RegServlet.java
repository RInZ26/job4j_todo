package ru.job4j.servlet.security;


import ru.job4j.tools.RegHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/security/reg.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String email = req.getParameter("email");
        String name = req.getParameter("name");
        String firstPass = req.getParameter("firstPassword");
        String secondPass = req.getParameter("secondPassword");

        if (!RegHelper.validateMail(email)) {
            finishWorkByError("Пользователь с такой почтой уже существует", req, resp);
            return;
        }

        if (!RegHelper.validatePasswords(firstPass, secondPass)) {
            finishWorkByError("Пароли не совпадают", req, resp);
            return;
        }

        RegHelper.saveUser(name, email, firstPass);
        resp.sendRedirect(req.getContextPath() + "/auth.do");
    }

    /**
     * Запрос на повторный ввод данных из-за провала одного валидатора
     *
     * @param errorMsg
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    private void finishWorkByError(String errorMsg, HttpServletRequest req,
                                   HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("error", errorMsg);
        req.getRequestDispatcher("/security/reg.jsp").forward(req, resp);
    }
}
