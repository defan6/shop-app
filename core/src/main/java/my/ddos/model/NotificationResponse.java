package my.ddos.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import my.ddos.enums.OrderStatus;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationResponse {
    private Long userId;
    private String transactionId;
    private Long orderId;
    private OrderStatus orderStatus;
    private String message;
}
