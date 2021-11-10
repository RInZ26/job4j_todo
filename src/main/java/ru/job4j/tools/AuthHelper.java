package ru.job4j.tools;

import ru.job4j.entity.JUser;
import ru.job4j.store.HbmStore;

import java.util.Optional;

public class AuthHelper {
    /**
     * Валидатор пользователя
     *
     * @param email
     * @param pass
     * @return найден и пароль совпал? user : null
     */
    public static JUser validateAuth(String email, String pass) {
        JUser user = HbmStore.getInst().findUserByEmail(email);
        return Optional.ofNullable(user).filter(u -> u.getPassword().equals(pass))
                .orElse(null);
    }
}
