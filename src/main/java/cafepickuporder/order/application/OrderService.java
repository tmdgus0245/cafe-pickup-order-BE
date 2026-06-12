package cafepickuporder.order.application;

import cafepickuporder.customer.domain.Customer;
import cafepickuporder.customer.infra.CustomerRepository;
import cafepickuporder.menu.domain.Menu;
import cafepickuporder.menu.domain.MenuOption;
import cafepickuporder.menu.infra.MenuOptionRepository;
import cafepickuporder.menu.infra.MenuRepository;
import cafepickuporder.order.domain.Order;
import cafepickuporder.order.domain.OrderItem;
import cafepickuporder.order.domain.OrderItemOption;
import cafepickuporder.order.domain.OrderStatus;
import cafepickuporder.order.dto.request.OrderCancelRequest;
import cafepickuporder.order.dto.request.OrderCreateRequest;
import cafepickuporder.order.dto.request.OrderItemCreateRequest;
import cafepickuporder.order.dto.request.OrderRejectRequest;
import cafepickuporder.order.dto.response.OrderCreateResponse;
import cafepickuporder.order.dto.response.StoreOrderResponse;
import cafepickuporder.order.infra.OrderItemOptionRepository;
import cafepickuporder.order.infra.OrderItemRepository;
import cafepickuporder.order.infra.OrderRepository;
import cafepickuporder.payment.domain.Payment;
import cafepickuporder.payment.domain.PaymentMethod;
import cafepickuporder.payment.infra.PaymentRepository;
import cafepickuporder.store.domain.Store;
import cafepickuporder.store.infra.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cafepickuporder.order.dto.response.OrderListResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderItemOptionRepository orderItemOptionRepository;
    private final PaymentRepository paymentRepository;

    private final CustomerRepository customerRepository;
    private final StoreRepository storeRepository;
    private final MenuRepository menuRepository;
    private final MenuOptionRepository menuOptionRepository;

    public OrderCreateResponse createOrder(Long customerId, OrderCreateRequest request) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("고객을 찾을 수 없습니다."));

        Store store = storeRepository.findById(request.getStoreId())
                .orElseThrow(() -> new IllegalArgumentException("매장을 찾을 수 없습니다."));

        int totalPrice = calculateTotalPrice(request.getItems());

        LocalDateTime requestedPickupTime = request.getRequestedPickupTime();

        LocalDateTime estimatedPickupTime = LocalDateTime.now()
                .plusMinutes(store.getAveragePreparationMinutes());

        Order order = new Order(
                customer,
                store,
                generateOrderNumber(),
                OrderStatus.REQUESTED,
                totalPrice,
                requestedPickupTime,
                estimatedPickupTime
        );

        Order savedOrder = orderRepository.save(order);

        saveOrderItems(savedOrder, request.getItems());

        Payment payment = createMockPayment(savedOrder, request.getPaymentMethod());

        return OrderCreateResponse.from(savedOrder, payment);
    }

    private Payment createMockPayment(Order order, PaymentMethod paymentMethod) {
        PaymentMethod method = paymentMethod == null
                ? PaymentMethod.MOCK_CARD
                : paymentMethod;

        Payment payment = new Payment(
                order,
                generatePaymentKey(),
                method,
                order.getTotalPrice()
        );

        return paymentRepository.save(payment);
    }

    private String generatePaymentKey() {
        return "MOCK-" + UUID.randomUUID()
                .toString()
                .replace("-", "")
                .substring(0, 16)
                .toUpperCase();
    }

    @Transactional(readOnly = true)
    public List<OrderListResponse> getOrders(Long customerId) {
        return orderRepository.findByCustomerIdOrderByCreatedAtDesc(customerId)
                .stream()
                .map(OrderListResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<StoreOrderResponse> getStoreOrders(Long storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new IllegalArgumentException("매장을 찾을 수 없습니다."));

        return orderRepository.findByStoreIdOrderByCreatedAtDesc(store.getId())
                .stream()
                .map(StoreOrderResponse::from)
                .toList();
    }

    private int calculateTotalPrice(List<OrderItemCreateRequest> items) {
        int totalPrice = 0;

        for (OrderItemCreateRequest itemRequest : items) {
            Menu menu = menuRepository.findById(itemRequest.getMenuId())
                    .orElseThrow(() -> new IllegalArgumentException("메뉴를 찾을 수 없습니다."));

            int optionTotalPrice = 0;

            if (itemRequest.getOptionIds() != null) {
                for (Long optionId : itemRequest.getOptionIds()) {
                    MenuOption option = menuOptionRepository.findById(optionId)
                            .orElseThrow(() -> new IllegalArgumentException("옵션을 찾을 수 없습니다."));

                    optionTotalPrice += option.getAdditionalPrice();
                }
            }

            totalPrice += (menu.getPrice() + optionTotalPrice) * itemRequest.getQuantity();
        }

        return totalPrice;
    }

    private void saveOrderItems(Order order, List<OrderItemCreateRequest> items) {
        for (OrderItemCreateRequest itemRequest : items) {
            Menu menu = menuRepository.findById(itemRequest.getMenuId())
                    .orElseThrow(() -> new IllegalArgumentException("메뉴를 찾을 수 없습니다."));

            List<MenuOption> options = List.of();

            if (itemRequest.getOptionIds() != null && !itemRequest.getOptionIds().isEmpty()) {
                options = menuOptionRepository.findAllById(itemRequest.getOptionIds());
            }

            int optionTotalPrice = options.stream()
                    .mapToInt(MenuOption::getAdditionalPrice)
                    .sum();

            int itemTotalPrice = (menu.getPrice() + optionTotalPrice) * itemRequest.getQuantity();

            OrderItem orderItem = new OrderItem(
                    order,
                    menu,
                    menu.getName(),
                    menu.getPrice(),
                    itemRequest.getQuantity(),
                    itemTotalPrice
            );

            OrderItem savedOrderItem = orderItemRepository.save(orderItem);

            for (MenuOption option : options) {
                OrderItemOption orderItemOption = new OrderItemOption(
                        savedOrderItem,
                        option.getName(),
                        option.getAdditionalPrice()
                );

                orderItemOptionRepository.save(orderItemOption);
            }
        }
    }

    private String generateOrderNumber() {
        return "ORD-" + UUID.randomUUID()
                .toString()
                .substring(0, 8)
                .toUpperCase();
    }

    private Order getStoreOrder(Long storeId, Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다."));

        if (!order.getStore().getId().equals(storeId)) {
            throw new IllegalArgumentException("해당 매장의 주문이 아닙니다.");
        }

        return order;
    }

    public StoreOrderResponse acceptOrder(Long storeId, Long orderId) {
        Order order = getStoreOrder(storeId, orderId);
        order.accept();

        return StoreOrderResponse.from(order);
    }

    public StoreOrderResponse markOrderReady(Long storeId, Long orderId) {
        Order order = getStoreOrder(storeId, orderId);
        order.markReady();

        return StoreOrderResponse.from(order);
    }

    public StoreOrderResponse completeOrder(Long storeId, Long orderId) {
        Order order = getStoreOrder(storeId, orderId);
        order.complete();

        return StoreOrderResponse.from(order);
    }

    public StoreOrderResponse rejectOrder(Long storeId, Long orderId, OrderRejectRequest request) {
        Order order = getStoreOrder(storeId, orderId);

        order.reject(request.getReason());
        cancelPaymentIfExists(order);

        return StoreOrderResponse.from(order);
    }

    public OrderCreateResponse cancelOrder(Long customerId, Long orderId, OrderCancelRequest request) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다."));

        if (!order.getCustomer().getId().equals(customerId)) {
            throw new IllegalArgumentException("해당 고객의 주문이 아닙니다.");
        }

        order.cancel(request.getReason());

        Payment payment = cancelPaymentIfExists(order);

        return OrderCreateResponse.from(order, payment);
    }

    private Payment cancelPaymentIfExists(Order order) {
        return paymentRepository.findByOrderId(order.getId())
                .map(payment -> {
                    payment.cancel();
                    return payment;
                })
                .orElse(null);
    }
}