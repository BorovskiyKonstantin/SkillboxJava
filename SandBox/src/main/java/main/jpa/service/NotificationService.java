package main.jpa.service;

import main.jpa.entity.Notification;
import main.jpa.entity.NotificationType;
import main.jpa.entity.NotificationTypeCode;
import main.jpa.repository.NotificationRepository;
import main.jpa.repository.NotificationTypeRepository;
import main.jpa.repository.PostCommentRepository;
import main.jpa.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private NotificationTypeRepository typeRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostCommentRepository postCommentRepository;

    public List<Notification> findAllNotifications(){
        return notificationRepository.findAll();
    }

    public List<NotificationType> findAllTypes(){
        return typeRepository.findAll();
    }

    public Notification findNotificationById(int id){
        return notificationRepository.findById(id).orElse(null);
    }

    public NotificationType findTypeById(int id){
        return typeRepository.findById(id).orElse(null);
    }

    public NotificationType findTypeByCode(NotificationTypeCode code){
        return typeRepository.findByCode(code).orElse(null);
    }

    public void saveNotification(Notification notification){
        notificationRepository.save(notification);
    }

}
