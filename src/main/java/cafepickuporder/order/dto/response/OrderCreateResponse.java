package cafepickuporder.order.dto.response;

import cafepickuporder.order.domain.Order;
import cafepickuporder.payment.domain.Payment;
import cafepickuporder.payment.dto.response.PaymentResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderCreateResponse {

    private Long orderId;
    private String orderNumber;
    private String status;
    private Integer totalPrice;
    private PaymentResponse payment;

    public static OrderCreateResponse from(Order order, Payment payment) {
        return new OrderCreateResponse(
                order.getId(),
                order.getOrderNumber(),
                order.getStatus().name(),
                order.getTotalPrice(),
                payment == null ? null : PaymentResponse.from(payment)
        );
    }
}