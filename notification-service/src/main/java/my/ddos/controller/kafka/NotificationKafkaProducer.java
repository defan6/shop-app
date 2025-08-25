package my.ddos.controller.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.ddos.util.KafkaUtil;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import static my.ddos.config.KafkaTopicConfig.NOTIFICATION_FAILURE_ORDER_TOPIC;
import static my.ddos.config.KafkaTopicConfig.NOTIFICATION_SUCCESS_ORDER_TOPIC;

@Component
@Slf4j
@RequiredArgsConstructor
public class NotificationKafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;


    private final KafkaUtil kafkaUtil;


    public void notificateAboutSuccessOrder(String message){
        Message<String> kafkaMessage = kafkaUtil.buildMessage(message, NOTIFICATION_SUCCESS_ORDER_TOPIC);

        kafkaTemplate.send(kafkaMessage);

        log.info("Message sent to {}", NOTIFICATION_SUCCESS_ORDER_TOPIC);
    }


    public void notificateAboutFailureOrder(String message){
        Message<String> kafkaMessage = kafkaUtil.buildMessage(message, NOTIFICATION_FAILURE_ORDER_TOPIC);

        kafkaTemplate.send(kafkaMessage);

        log.info("Message sent to {}", NOTIFICATION_FAILURE_ORDER_TOPIC);
    }
}
