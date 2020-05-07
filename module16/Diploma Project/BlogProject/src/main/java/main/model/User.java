package response;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

/**
 *      users   -   пользователи
 *       ● id   INT   NOT   NULL   AUTO_INCREMENT
 *       ● is_moderator   TINYINT   NOT   NULL   -   является   ли   пользователь   модератором  (может   ли   править   глобальные   настройки   сайта   и   модерировать   посты)
 *       ● reg_time   DATETIME   NOT   NULL   -   дата   и   время   регистрации   пользователя
 *       ● name   VARCHAR(255)   NOT   NULL   -   имя   пользователя
 *       ● email   VARCHAR(255)   NOT   NULL   -   e-mail   пользователя
 *       ● password   VARCHAR(255)   NOT   NULL   -   хэш   пароля   пользователя  ● code   VARCHAR(255)   -   код   для   восстановления   пароля,   может   быть   NULL
 *       ● photo   TEXT   -   фотография   (ссылка   на   файл),   может   быть   NULL
 */
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "is_moderator")
    private byte isModerator;

    @Column(name = "reg_time")
    private Timestamp regTime;

    private String name;
    private String email;
    private String password;
    private String photo;
}
