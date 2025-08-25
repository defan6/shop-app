package my.ddos.controller.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.ddos.model.PaymentResponse;
import my.ddos.service.NotificationService;
import my.ddos.util.JsonConverter;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.KafkaListeners;
import org.springframework.stereotype.Component;

import static my.ddos.config.KafkaTopicConfig.*;


@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationKafkaConsumer {

    private final JsonConverter jsonConverter;


    private final NotificationService notificationService;

    @KafkaListener(topics = PAYMENT_SUCCESS_TOPIC, groupId = "notification-group")
    public void notificateAboutSuccessOrder(String message){
        PaymentResponse paymentResponse = jsonConverter.toObject(message, PaymentResponse.class);
        log.info("Recieved message from {}, message: {}", PAYMENT_SUCCESS_TOPIC, message);
        notificationService.notificateAboutSuccessOrder(paymentResponse);
    }


    @KafkaListener(topics = PAYMENT_DEAD_LETTER_TOPIC, groupId = "notification-group")
    public void notificateAboutFailureOrder(String message){
        PaymentResponse paymentResponse = jsonConverter.toObject(message, PaymentResponse.class);
        log.info("Recieved message from {}, message: {}", PAYMENT_DEAD_LETTER_TOPIC, message);
        notificationService.notificateAboutFailureOrder(paymentResponse);
    }
}
