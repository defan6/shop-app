package my.ddos.controller.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.ddos.model.OrderResponse;
import my.ddos.util.JsonConverter;
import my.ddos.util.KafkaUtil;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import static my.ddos.config.KafkaTopicConfig.ORDER_TOPIC;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaOrderProducer {

    private final KafkaUtil kafkaUtil;


    private final KafkaTemplate<String, String> kafkaTemplate;


    private final JsonConverter jsonConverter;


    public void produceOrder(OrderResponse orderResponse){
        String message = jsonConverter.toJson(orderResponse);
        Message<String> kafkaMessage = kafkaUtil.buildMessage(message, ORDER_TOPIC);
        log.info("Order send to {}", ORDER_TOPIC);
        kafkaTemplate.send(kafkaMessage);
    }
}
