package cafepickuporder.payment.domain;

import cafepickuporder.order.domain.Order;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "payments")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String paymentKey;

    @Enumerated(EnumType.STRING)
    private PaymentMethod method;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private Integer amount;

    private LocalDateTime paidAt;
    private LocalDateTime canceledAt;
    private LocalDateTime refundedAt;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    public Payment(
            Order order,
            String paymentKey,
            PaymentMethod method,
            Integer amount
    ) {
        this.order = order;
        this.paymentKey = paymentKey;
        this.method = method;
        this.status = PaymentStatus.PAID;
        this.amount = amount;
        this.paidAt = LocalDateTime.now();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void cancel() {
        if (this.status != PaymentStatus.PAID) {
            throw new IllegalStateException("결제 완료 상태에서만 결제를 취소할 수 있습니다.");
        }

        this.status = PaymentStatus.CANCELED;
        this.canceledAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}