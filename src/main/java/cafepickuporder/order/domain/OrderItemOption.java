package cafepickuporder.order.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "order_item_options")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItemOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String optionName;
    private Integer optionPrice;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_item_id")
    private OrderItem orderItem;

    public OrderItemOption(
            OrderItem orderItem,
            String optionName,
            Integer optionPrice
    ) {
        this.orderItem = orderItem;
        this.optionName = optionName;
        this.optionPrice = optionPrice;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}