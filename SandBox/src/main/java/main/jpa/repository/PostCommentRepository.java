package main.jpa.repository;

import main.jpa.entity.Notification;
import main.jpa.entity.PostComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostCommentRepository extends JpaRepository<PostComment,Integer> {

}
