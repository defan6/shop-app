package my.ddos.service;

import my.ddos.model.PaymentResponse;

public interface NotificationService {


    void notificateAboutSuccessOrder(PaymentResponse paymentResponse);


    void notificateAboutFailureOrder(PaymentResponse paymentResponse);
}
