package my.ddos.controller.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.ddos.model.OrderResponse;
import my.ddos.service.PaymentService;
import my.ddos.util.JsonConverter;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static my.ddos.config.KafkaTopicConfig.ORDER_TOPIC;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumer {

    private final JsonConverter jsonConverter;


    private final PaymentService paymentService;

    @KafkaListener(topics = ORDER_TOPIC, groupId = "paymentListeners")
    public void consumeOrderMessage(String message){
        log.info("Recieved message from {}, message: {}", ORDER_TOPIC, message);
        OrderResponse orderResponse = jsonConverter.toObject(message, OrderResponse.class);
        paymentService.doPayment(orderResponse);

    }
}
