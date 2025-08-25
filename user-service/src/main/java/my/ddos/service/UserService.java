package my.ddos.service;

import my.ddos.model.entity.Notification;

import java.util.List;

public interface UserService {
    List<Notification> getAllNotificationByUserId(Long id);
}
