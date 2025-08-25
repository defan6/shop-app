package my.ddos.controller.rest;

import lombok.RequiredArgsConstructor;
import my.ddos.model.OrderResponse;
import my.ddos.model.dto.OrderRequest;
import my.ddos.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {


    private final OrderService orderService;


    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest request){
        return ResponseEntity.ok(orderService.createOrder(request));
    }


    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long id){
        return ResponseEntity.ok(orderService.getOrderById(id));
    }
}
