package cafepickuporder.order.dto.response;

import cafepickuporder.order.domain.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class StoreOrderResponse {

    private Long orderId;
    private String orderNumber;
    private Long customerId;
    private String customerName;
    private String status;
    private Integer totalPrice;
    private LocalDateTime requestedPickupTime;
    private LocalDateTime estimatedPickupTime;
    private LocalDateTime createdAt;

    public static StoreOrderResponse from(Order order) {
        return new StoreOrderResponse(
                order.getId(),
                order.getOrderNumber(),
                order.getCustomer().getId(),
                order.getCustomer().getName(),
                order.getStatus().name(),
                order.getTotalPrice(),
                order.getRequestedPickupTime(),
                order.getEstimatedPickupTime(),
                order.getCreatedAt()
        );
    }
}