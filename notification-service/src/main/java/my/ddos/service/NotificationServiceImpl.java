package my.ddos.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.ddos.controller.kafka.NotificationKafkaProducer;
import my.ddos.enums.OrderStatus;
import my.ddos.model.NotificationResponse;
import my.ddos.model.PaymentResponse;
import my.ddos.util.JsonConverter;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService{

    private static final String SUCCESS_NOTIFICATION_MESSAGE = "Thank you for your purchase";


    private static final String FAILURE_NOTIFICATION_MESSAGE = "The order was not accepted.";

    private final NotificationKafkaProducer notificationKafkaProducer;


    private final JsonConverter jsonConverter;

    @Override
    public void notificateAboutSuccessOrder(PaymentResponse paymentResponse) {

        NotificationResponse notificationResponse = NotificationResponse.builder()
                .orderId(paymentResponse.getOrderId())
                .orderStatus(OrderStatus.PAID)
                .transactionId(paymentResponse.getTransactionId())
                .userId(paymentResponse.getUserId())
                .message(SUCCESS_NOTIFICATION_MESSAGE)
                .build();
        String successMessage = jsonConverter.toJson(notificationResponse);
        notificationKafkaProducer.notificateAboutSuccessOrder(successMessage);

    }

    @Override
    public void notificateAboutFailureOrder(PaymentResponse paymentResponse) {
        NotificationResponse notificationResponse = NotificationResponse.builder()
                .orderId(paymentResponse.getOrderId())
                .orderStatus(OrderStatus.CANCELLED)
                .userId(paymentResponse.getUserId())
                .transactionId(paymentResponse.getTransactionId())
                .message(FAILURE_NOTIFICATION_MESSAGE)
                .build();
        String failureMessage = jsonConverter.toJson(notificationResponse);
        notificationKafkaProducer.notificateAboutFailureOrder(failureMessage);
    }
}
