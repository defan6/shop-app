package my.ddos.service;


import lombok.RequiredArgsConstructor;
import my.ddos.controller.kafka.KafkaPaymentProducer;
import my.ddos.enums.PaymentStatus;
import my.ddos.model.OrderResponse;
import my.ddos.model.PaymentResponse;
import my.ddos.model.entity.Payment;
import my.ddos.repository.PaymentRepository;
import my.ddos.util.JsonConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final KafkaPaymentProducer kafkaPaymentProducer;

    private final PaymentRepository paymentRepository;


    private final JsonConverter jsonConverter;
    @Override
    @Transactional
    public void doPayment(OrderResponse orderResponse) {
        boolean success = ThreadLocalRandom.current().nextBoolean();

        PaymentResponse paymentResponse = new PaymentResponse();
        paymentResponse.setPaymentStatus(success ? PaymentStatus.SUCCESS : PaymentStatus.FAILED);
        paymentResponse.setOrderId(orderResponse.getId());
        paymentResponse.setTransactionId(UUID.randomUUID().toString());
        paymentResponse.setUserId(orderResponse.getUserId());
        Payment payment = Payment.builder()
                .orderId(paymentResponse.getOrderId())
                .paymentStatus(paymentResponse.getPaymentStatus())
                .userId(paymentResponse.getUserId())
                .transactionId(paymentResponse.getTransactionId())
                .build();
        paymentRepository.save(payment);
        String message = jsonConverter.toJson(paymentResponse);
        if(success){
            kafkaPaymentProducer.sendToSuccessTopic(message);
        }
        else{
            kafkaPaymentProducer.sendToDeadLetterTopic(message);
        }
    }
}
