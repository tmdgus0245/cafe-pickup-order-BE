package cafepickuporder.customer.domain;

import cafepickuporder.store.domain.Store;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        name = "customer_favorite_stores",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_customer_favorite_store",
                        columnNames = {"customer_id", "store_id"}
                )
        }
)
public class CustomerFavoriteStore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public CustomerFavoriteStore(Customer customer, Store store) {
        this.customer = customer;
        this.store = store;
        this.createdAt = LocalDateTime.now();
    }
}