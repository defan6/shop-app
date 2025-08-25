package my.ddos.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import my.ddos.enums.PaymentStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponse {
    private Long orderId;
    private Long userId;
    private PaymentStatus paymentStatus;
    private String transactionId;
}
