package main;

import main.jpa.entity.CommentComment;
import main.jpa.entity.Notification;
import main.jpa.entity.Post;
import main.jpa.entity.PostComment;
import main.jpa.service.NotificationService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.List;


/**
 * Нужно сопоставить классы сущностей с их типом. Нужно ли сейчас создать ЗАГЛУШКИ все Entity для post, post_comment, message, friendship? +
 * Для сущности PostComment нужно будет создать сущность-наследника CommentComment.+
 * Чем заполнять поле name в notification_type? NULL
 * По дефолту все поля nullable? -
 */
@SpringBootApplication
public class Main {


    public static void main(String[] args) {

        ApplicationContext context = SpringApplication.run(Main.class);

        NotificationService notificationService = context.getBean(NotificationService.class);

        //Пробуем создать и добавить в БД новые Notification с разными entity
        //Post
        Post post = new Post();
        post.setName("myPost");

        Notification notificationPost = new Notification();
        notificationPost.setEntity(post);
        notificationService.saveNotification(notificationPost);

        //PostComment
        PostComment postComment = new PostComment();
        postComment.setName("Комментарий к посту");
        postComment.setParentId(null);

        Notification postCommentNotification = new Notification();
        postCommentNotification.setEntity(postComment);
        notificationService.saveNotification(postCommentNotification);

        //CommentComment
//        PostComment commentComment = new PostComment();     //Так получется type_id = 2
        PostComment commentComment = new CommentComment();     //Так получется type_id = 3
        commentComment.setName("Комментарий к комментарию");
        commentComment.setParentId(1);

        Notification commentCommentNotification = new Notification();
        commentCommentNotification.setEntity(commentComment);
        notificationService.saveNotification(commentCommentNotification);



        //Вывод всех всех Notification из БД
        List<Notification> notifications;
        try {
            notifications = notificationService.findAllNotifications();
            notifications.forEach(n -> {
                try {
                    System.out.println("id: " + n.getId() + " || type id: " + n.getNotificationType().getId() + " || entity class: " + n.getEntity().getClass().getSimpleName());
                }
                catch (NullPointerException e){
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }



    }

}