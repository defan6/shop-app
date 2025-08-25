package my.ddos.service;

import my.ddos.model.OrderResponse;

public interface PaymentService {

    void doPayment(OrderResponse orderResponse);
}
