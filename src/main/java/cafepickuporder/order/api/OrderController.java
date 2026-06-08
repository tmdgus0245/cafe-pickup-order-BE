package cafepickuporder.order.api;

import cafepickuporder.order.application.OrderService;
import cafepickuporder.order.dto.request.OrderCreateRequest;
import cafepickuporder.order.dto.response.OrderCreateResponse;
import cafepickuporder.order.dto.response.OrderListResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderCreateResponse createOrder(
            @RequestParam Long customerId,
            @RequestBody OrderCreateRequest request
    ) {
        return orderService.createOrder(customerId, request);
    }

    @GetMapping
    public List<OrderListResponse> getOrders(
            @RequestParam Long customerId
    ) {
        return orderService.getOrders(customerId);
    }
}