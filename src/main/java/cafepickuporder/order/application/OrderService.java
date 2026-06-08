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
import cafepickuporder.order.dto.request.OrderCreateRequest;
import cafepickuporder.order.dto.request.OrderItemCreateRequest;
import cafepickuporder.order.dto.response.OrderCreateResponse;
import cafepickuporder.order.infra.OrderItemOptionRepository;
import cafepickuporder.order.infra.OrderItemRepository;
import cafepickuporder.order.infra.OrderRepository;
import cafepickuporder.store.domain.Store;
import cafepickuporder.store.infra.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        return OrderCreateResponse.from(savedOrder);
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
}