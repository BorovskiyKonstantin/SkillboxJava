use testdb;
DROP TABLE IF EXISTS notification, notification_type, post, post_comment;

CREATE TABLE `notification_type`(
`id` int(11) AUTO_INCREMENT,
`code` varchar(45),
`name` varchar(45),
PRIMARY KEY (`id`),
UNIQUE KEY `code_UNIQUE` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO notification_type (code, name) VALUES
("POST", "Новый пост"),
("POST_COMMENT","Комментарий к посту"),
("COMMENT_COMMENT","Ответ на комментарий"),
("FRIEND_REQUEST","Запрос дружбы"),
("MESSAGE","Личное сообщение");

CREATE TABLE `notification` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type_id` int(11) DEFAULT NULL,
  `sent_time` TIMESTAMP,
  `person_id` int(11),
  `entity_id` int DEFAULT NULL,
  `contact` varchar(255),
  PRIMARY KEY (`id`),
  KEY `FK_notification_type_id_idx` (`type_id`),
  CONSTRAINT `FK_notification_type_id` FOREIGN KEY (`type_id`) REFERENCES `notification_type` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO notification (entity_id, type_id) VALUES
(1, 1),
(2, 1),
(3, 1),
(1, 2),
(2, 2),
(3, 3),
(4, 3);

CREATE TABLE `post`(
`id` int AUTO_INCREMENT,
`name` varchar(45),
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
INSERT INTO post (name) VALUES
("Post 1"),
("Post 2"),
("Post 3"),
("Post 4");

CREATE TABLE `post_comment`(
`id` int AUTO_INCREMENT,
`name` varchar(45),
`parent_id` int,
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
INSERT INTO post_comment (name, parent_id) VALUES
("post comment 1", null),
("post comment 2", null),
("comment comment 1", 1),
("comment comment 2", 2),
("post comment 3", null),
("comment comment 3", 2);

