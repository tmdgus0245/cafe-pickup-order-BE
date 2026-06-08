package cafepickuporder.order.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class OrderCreateRequest {

    private Long storeId;
    private LocalDateTime requestedPickupTime;
    private List<OrderItemCreateRequest> items;
}