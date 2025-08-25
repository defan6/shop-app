package my.ddos.controller.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.ddos.model.PaymentResponse;
import my.ddos.service.OrderService;
import my.ddos.util.JsonConverter;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static my.ddos.config.KafkaTopicConfig.PAYMENT_DEAD_LETTER_TOPIC;
import static my.ddos.config.KafkaTopicConfig.PAYMENT_SUCCESS_TOPIC;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaOrderConsumer {

    private final OrderService orderService;

    private final JsonConverter jsonConverter;

    @KafkaListener(topics = {PAYMENT_SUCCESS_TOPIC, PAYMENT_DEAD_LETTER_TOPIC}, groupId = "order-rollback")
    public void rollbackOrder(ConsumerRecord<String, String> record){
        String message = record.value();
        String topic = record.topic();
        PaymentResponse paymentResponse = jsonConverter.toObject(message, PaymentResponse.class);
        if(topic.equalsIgnoreCase(PAYMENT_SUCCESS_TOPIC)){
            orderService.rollbackSuccessOrder(paymentResponse);
        }
        else if(topic.equalsIgnoreCase(PAYMENT_DEAD_LETTER_TOPIC)){
            orderService.rollbackFailureOrder(paymentResponse);
        }
    }
}
