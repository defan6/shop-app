package my.ddos.controller.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.ddos.util.KafkaUtil;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import static my.ddos.config.KafkaTopicConfig.*;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaPaymentProducer {

    private final KafkaUtil kafkaUtil;

    private final KafkaTemplate<String, String> kafkaTemplate;
    public void sendToSuccessTopic(String message){
        Message<String> kafkaMessage = kafkaUtil.buildMessage(message, PAYMENT_SUCCESS_TOPIC);
        kafkaTemplate.send(kafkaMessage);
        log.info("Send message to {}, message: {}", PAYMENT_SUCCESS_TOPIC, kafkaMessage);
    }


    public void sendToDeadLetterTopic(String message){
        Message<String> kafkaMessage = kafkaUtil.buildMessage(message, PAYMENT_DEAD_LETTER_TOPIC);
        kafkaTemplate.send(kafkaMessage);
        log.info("Send message to {}, message: {}", PAYMENT_DEAD_LETTER_TOPIC, kafkaMessage);
    }
}
