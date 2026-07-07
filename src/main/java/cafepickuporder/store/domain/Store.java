package cafepickuporder.store.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "stores")
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private String address;

    private String detailAddress;

    private String phone;

    @Column(precision = 10, scale = 7)
    private BigDecimal latitude;

    @Column(precision = 10, scale = 7)
    private BigDecimal longitude;

    private LocalTime openTime;

    private LocalTime closeTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StoreStatus status;

    @Column(nullable = false)
    private boolean appOrderAvailable;

    @Column(nullable = false)
    private boolean dineInAvailable;

    private Integer averagePreparationMinutes;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @Builder
    public Store(
            String name,
            String description,
            String address,
            String detailAddress,
            String phone,
            BigDecimal latitude,
            BigDecimal longitude,
            LocalTime openTime,
            LocalTime closeTime,
            StoreStatus status,
            boolean appOrderAvailable,
            boolean dineInAvailable,
            Integer averagePreparationMinutes
    ) {
        this.name = name;
        this.description = description;
        this.address = address;
        this.detailAddress = detailAddress;
        this.phone = phone;
        this.latitude = latitude;
        this.longitude = longitude;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.status = status;
        this.appOrderAvailable = appOrderAvailable;
        this.dineInAvailable = dineInAvailable;
        this.averagePreparationMinutes = averagePreparationMinutes;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void deactivate() {
        this.status = StoreStatus.INACTIVE;
        this.appOrderAvailable = false;
        this.updatedAt = LocalDateTime.now();
    }
}