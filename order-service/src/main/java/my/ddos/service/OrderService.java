package my.ddos.service;

import my.ddos.model.OrderResponse;
import my.ddos.model.PaymentResponse;
import my.ddos.model.dto.OrderRequest;

public interface OrderService {
    OrderResponse createOrder(OrderRequest request);

    OrderResponse getOrderById(Long id);

    void rollbackSuccessOrder(PaymentResponse paymentResponse);

    void rollbackFailureOrder(PaymentResponse paymentResponse);
}
