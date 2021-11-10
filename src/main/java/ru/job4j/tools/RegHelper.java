package ru.job4j.tools;

import ru.job4j.entity.JUser;
import ru.job4j.store.HbmStore;

import java.util.Objects;

public class RegHelper {

    /**
     * Валидация регистрации пользователя на предмет совпадения почтовых ящиков
     *
     * @param email
     * @return true, если valid
     */
    public static boolean validateMail(String email) {
        return Objects.isNull(HbmStore.getInst().findUserByEmail(email));
    }

    /**
     * Валидация паролей (отдельно, потому что можно накрутить логику проверки наличия цифрк и прочей шляпы)
     *
     * @param passFirst
     * @param passSecond
     * @return true, если valid
     */
    public static boolean validatePasswords(String passFirst, String passSecond) {
        return passFirst.equals(passSecond);
    }


    /**
     * Прослойка, чтобы не вызывать Store из сервлета
     *
     * @param name
     * @param email
     * @param pass
     * @return
     */
    public static void saveUser(String name, String email, String pass) {
        HbmStore.getInst().<JUser>add(JUser.builder().name(name).email(email).password(pass).build());
    }
}
