package my.ddos.service;

import my.ddos.model.NotificationResponse;
import my.ddos.model.entity.Notification;

import java.util.List;

public interface NotificationService {

    List<NotificationResponse> getAllNotificationsByUserId(Long userId);

    void save(NotificationResponse notificationResponse);
}
