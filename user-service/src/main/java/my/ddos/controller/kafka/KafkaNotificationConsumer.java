package my.ddos.controller.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.ddos.model.NotificationResponse;
import my.ddos.service.NotificationService;
import my.ddos.util.JsonConverter;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static my.ddos.config.KafkaTopicConfig.NOTIFICATION_FAILURE_ORDER_TOPIC;
import static my.ddos.config.KafkaTopicConfig.NOTIFICATION_SUCCESS_ORDER_TOPIC;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaNotificationConsumer {


    private final JsonConverter jsonConverter;


    private final NotificationService notificationService;

    @KafkaListener(topics = {NOTIFICATION_SUCCESS_ORDER_TOPIC, NOTIFICATION_FAILURE_ORDER_TOPIC}, groupId = "group")
    public void consumeNotificate(String message){
        NotificationResponse notificationResponse = jsonConverter.toObject(message, NotificationResponse.class);
        log.info("User service recieved message {}", message);
        notificationService.save(notificationResponse);

    }
}
