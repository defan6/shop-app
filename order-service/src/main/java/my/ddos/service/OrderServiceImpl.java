package my.ddos.service;


import lombok.RequiredArgsConstructor;
import my.ddos.controller.kafka.KafkaOrderProducer;
import my.ddos.enums.OrderStatus;
import my.ddos.mapper.OrderMapper;
import my.ddos.model.OrderResponse;
import my.ddos.model.PaymentResponse;
import my.ddos.model.dto.OrderRequest;
import my.ddos.model.entity.Order;
import my.ddos.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderMapper orderMapper;


    private final OrderRepository orderRepository;


    private final KafkaOrderProducer kafkaOrderProducer;

    @Override
    public OrderResponse createOrder(OrderRequest request) {
        Order order = orderMapper.toEntity(request);
        order.setOrderStatus(OrderStatus.NEW);
        order.setCreatedAt(LocalDateTime.now());
        Order savedOrder = orderRepository.save(order);
        OrderResponse orderResponse = orderMapper.toResponse(savedOrder);
        orderResponse.setCreatedAt(savedOrder.getCreatedAt());
        kafkaOrderProducer.produceOrder(orderResponse);
        return orderResponse;
    }


    @Override
    public void rollbackSuccessOrder(PaymentResponse paymentResponse) {
        Order order = orderRepository.findById(paymentResponse.getOrderId()).orElseThrow();
        order.setOrderStatus(OrderStatus.PAID);
        orderRepository.save(order);
    }

    @Override
    public void rollbackFailureOrder(PaymentResponse paymentResponse) {
        Order order = orderRepository.findById(paymentResponse.getOrderId()).orElseThrow();
        order.setOrderStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
    }

    @Override
    public OrderResponse getOrderById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
        return orderMapper.toResponse(order);
    }
}
