----------------------------------------ВЫЖИМКА ИЗ ЛЕКЦИИ

При использовании аннотации @ManyToMany поле должно быть ассоциировано с коллекцией объектов,
 а не единичным объектом.
 mappedBy используется для определения 'хозяина' ассоциации

----------------------------------------ENTITIES ДЛЯ HBMRUN В hibernate.cfg

        <mapping class="ru.job4j.lesson.expirements.manytomany.Person"/>
        <mapping class="ru.job4j.lesson.expirements.manytomany.Address"/>



----------------------------------------МЫСЛИ
Сама суть ManyToMany думаю понятна - просто создается доп таблица, где прописаны отношения двух классов.
Главное обратить внимание на CascadeType.ALL, потому что Remove хоть и здесь не выполняется, может
грохнуть вообще всю схему чуть ли не.

А ЕЩЕ ОЧЕНЬ ИНТЕРЕСНОЕ НАБЛЮДЕНИЕ
 Если Author many to many Book
 Но именно у Author лист Book
 То хер я могу удалить Book просто так. Сначала нужно удалить ее у автора (ПРИЧЕМ ВСЕХ) (remove from list)

 А вот удалить Author - легко. Связи с книгами спокойной сотрутся

 Ещё обязательно делать в качестве коллекций SET, а не листы - потому что последние выполняют миллион лишних операций

