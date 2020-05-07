package main.jpa.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "notification_type")
public class NotificationType {
    @Id
    private int id;

    @Column(name = "code")
    @Enumerated(EnumType.STRING)
    private NotificationTypeCode code;

    @Column(name = "name")
    private String name;

}
