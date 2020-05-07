package main.jpa.entity;

import lombok.Data;
import org.hibernate.annotations.Any;
import org.hibernate.annotations.AnyMetaDef;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.MetaValue;

import javax.persistence.*;
import java.sql.Timestamp;


@Entity
@Data
@Table(name = "notification")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "type_id", insertable = false, updatable = false)
    private NotificationType notificationType;

    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @Any(metaColumn = @Column(name = "type_id"))
    @AnyMetaDef(idType = "int", //тип значения id для целевых объектов в колонке entity_id
            metaType = "int",   //тип значения в колонке с type_id для идентификации класса
            metaValues = {
                    //Здесь сопоставляются классы сущностей и значение соответвующего им type_id, который будет проставлен автоматически
                    @MetaValue(targetEntity = Post.class, value = "1"),            //POST
                    @MetaValue(targetEntity = PostComment.class, value = "2"),     //POST_COMMENT
                    @MetaValue(targetEntity = CommentComment.class, value = "3")     //COMMENT_COMMENT
//                    @MetaValue(targetEntity = Friendship.class, value = "4"),      //FRIEND_REQUEST
//                    @MetaValue(targetEntity = Message.class, value = "5")         //MESSAGE
            })
    @JoinColumn(name="entity_id")
    private Object entity;

    @Column(name = "sent_time", columnDefinition = "TIMESTAMP")
    private Timestamp sentTime;

    @Column(name = "person_id")
    private Integer personId;

    @Column(name = "contact")
    private String contact;


}


