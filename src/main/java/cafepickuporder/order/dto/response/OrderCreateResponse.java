package cafepickuporder.order.dto.response;

import cafepickuporder.order.domain.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderCreateResponse {

    private Long orderId;
    private String orderNumber;
    private String status;
    private Integer totalPrice;

    public static OrderCreateResponse from(Order order) {
        return new OrderCreateResponse(
                order.getId(),
                order.getOrderNumber(),
                order.getStatus().name(),
                order.getTotalPrice()
        );
    }
}