package my.ddos.service;

import lombok.RequiredArgsConstructor;
import my.ddos.model.NotificationResponse;
import my.ddos.model.entity.Notification;
import my.ddos.model.entity.User;
import my.ddos.repository.NotificationRepository;
import my.ddos.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService{

    private final NotificationRepository notificationRepository;

    private final UserRepository userRepository;

    @Override
    public List<NotificationResponse> getAllNotificationsByUserId(Long userId) {
        List<Notification> notifications = notificationRepository.findAllByUserId(userId);
        return notifications.stream().map(this::mapToDto).toList();
    }


    @Override
    public void save(NotificationResponse notificationResponse) {
        User user = userRepository.findById(notificationResponse.getUserId()).orElseThrow(() -> new RuntimeException());
        Notification notification = mapToEntity(notificationResponse, user);
        notificationRepository.save(notification);
    }


    private NotificationResponse mapToDto(Notification n) {
        NotificationResponse notificationResponse = NotificationResponse.builder()
                .transactionId(n.getTransactionId())
                .message(n.getMessage())
                .orderId(n.getOrderId())
                .orderStatus(n.getOrderStatus())
                .userId(n.getUser() != null ? n.getUser().getId() : null)
                .build();
        return notificationResponse;
    }


    private Notification mapToEntity(NotificationResponse nr, User user){
        Notification notification = Notification.builder()
                .message(nr.getMessage())
                .orderId(nr.getOrderId())
                .orderStatus(nr.getOrderStatus())
                .transactionId(nr.getTransactionId())
                .user(user)
                .build();
        return notification;
    }
}
