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

    public void accept() {
        validateStatus(OrderStatus.REQUESTED);
        this.status = OrderStatus.ACCEPTED;
        this.acceptedAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void markReady() {
        validateStatus(OrderStatus.ACCEPTED);
        this.status = OrderStatus.READY;
        this.readyAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void complete() {
        validateStatus(OrderStatus.READY);
        this.status = OrderStatus.COMPLETED;
        this.completedAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void reject(String reason) {
        validateStatus(OrderStatus.REQUESTED);
        this.status = OrderStatus.REJECTED;
        this.rejectReason = reason;
        this.rejectedAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void cancel(String reason) {
        validateStatus(OrderStatus.REQUESTED);
        this.status = OrderStatus.CANCELED;
        this.cancelReason = reason;
        this.canceledAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    private void validateStatus(OrderStatus expectedStatus) {
        if (this.status != expectedStatus) {
            throw new IllegalStateException("Invalid order status transition.");
        }
    }
}