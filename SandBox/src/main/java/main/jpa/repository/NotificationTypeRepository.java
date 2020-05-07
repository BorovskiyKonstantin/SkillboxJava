package main.jpa.repository;

import main.jpa.entity.NotificationType;
import main.jpa.entity.NotificationTypeCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.expression.spel.ast.TypeCode;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NotificationTypeRepository extends JpaRepository<NotificationType, Integer> {

    @Override
    Optional<NotificationType> findById(Integer integer);

    Optional<NotificationType> findByCode(NotificationTypeCode code);
}
