package cafepickuporder.order.dto.response;

import cafepickuporder.order.domain.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class OrderListResponse {

    private Long orderId;
    private String orderNumber;
    private String storeName;
    private String status;
    private Integer totalPrice;
    private LocalDateTime requestedPickupTime;
    private LocalDateTime estimatedPickupTime;
    private LocalDateTime createdAt;

    public static OrderListResponse from(Order order) {
        return new OrderListResponse(
                order.getId(),
                order.getOrderNumber(),
                order.getStore().getName(),
                order.getStatus().name(),
                order.getTotalPrice(),
                order.getRequestedPickupTime(),
                order.getEstimatedPickupTime(),
                order.getCreatedAt()
        );
    }
}