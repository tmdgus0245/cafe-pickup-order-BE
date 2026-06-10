package cafepickuporder.order.api;

import cafepickuporder.global.security.StoreAccountPrincipal;
import cafepickuporder.order.application.OrderService;
import cafepickuporder.order.dto.response.StoreOrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stores/{storeId}/orders")
@RequiredArgsConstructor
public class StoreOrderController {

    private final OrderService orderService;

    @GetMapping
    public List<StoreOrderResponse> getStoreOrders(
            @PathVariable Long storeId,
            Authentication authentication
    ) {
        StoreAccountPrincipal principal =
                (StoreAccountPrincipal) authentication.getPrincipal();

        if (!principal.getStoreId().equals(storeId)) {
            throw new AccessDeniedException("해당 매장의 주문을 조회할 권한이 없습니다.");
        }

        return orderService.getStoreOrders(storeId);
    }
}