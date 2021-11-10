----------------------------------------ВЫЖИМКА ИЗ ЛЕКЦИИ

В модели "Пользователь" есть поле Role. Это поле содержит целый объект.

Hibernate загрузит этот объект сам.

Аннотации.

@ManyToOne
@JoinColumn(name = "role_id")
Указывают, что связь между таблицами - many-to-one и указывают по какому полю идет связь.


Hibernate загрузил модель Role с моделью User. Если бы мы использовали JDBC, то для модуля Role
пришлось бы писать отдельный запрос.

Обратите внимание: при сохранении модели User Hibernate не требует весь объект Role. Ему нужен только ключ.

Пример.

Role role = create(Role.of("ADMIN"), sf);
Role recreated = new Role();
recreated.setId(role.getId());
create(User.of("Petr Arsentev", recreated), sf);


----------------------------------------ENTITIES ДЛЯ HBMRUN В hibernate.cfg

        <mapping class="ru.job4j.lesson.expirements.manyToOne.User" />
        <mapping class="ru.job4j.lesson.expirements.manyToOne.Role" />



----------------------------------------МЫСЛИ
Здесь получается идёт простая идея.

User содержит ссылку на Role. Которая прикреплена к нему как к полю.

Связь User и Role   - ManyToOne.
Потому что у МНОГИХ User может быть ОДНА Role.
Пользователь может иметь только одну роль.

