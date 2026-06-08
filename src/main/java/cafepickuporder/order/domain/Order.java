package cafepickuporder.order.domain;

import cafepickuporder.customer.domain.Customer;
import cafepickuporder.store.domain.Store;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orderNumber;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private Integer totalPrice;

    private LocalDateTime requestedPickupTime;
    private LocalDateTime estimatedPickupTime;

    private LocalDateTime acceptedAt;
    private LocalDateTime readyAt;
    private LocalDateTime completedAt;
    private LocalDateTime rejectedAt;
    private LocalDateTime canceledAt;

    private String rejectReason;
    private String cancelReason;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    public Order(
            Customer customer,
            Store store,
            String orderNumber,
            OrderStatus status,
            Integer totalPrice,
            LocalDateTime requestedPickupTime,
            LocalDateTime estimatedPickupTime
    ) {
        this.customer = customer;
        this.store = store;
        this.orderNumber = orderNumber;
        this.status = status;
        this.totalPrice = totalPrice;
        this.requestedPickupTime = requestedPickupTime;
        this.estimatedPickupTime = estimatedPickupTime;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}