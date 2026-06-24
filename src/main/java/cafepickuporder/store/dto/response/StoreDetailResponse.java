package cafepickuporder.store.dto.response;

import cafepickuporder.store.domain.Store;
import cafepickuporder.store.domain.StoreStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalTime;

@Getter
@AllArgsConstructor
public class StoreDetailResponse {

    private Long storeId;
    private String name;
    private String description;
    private String address;
    private String detailAddress;
    private String phone;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private LocalTime openTime;
    private LocalTime closeTime;
    private StoreStatus status;
    private boolean appOrderAvailable;
    private boolean dineInAvailable;
    private Integer averagePreparationMinutes;

    public static StoreDetailResponse from(Store store) {
        return new StoreDetailResponse(
                store.getId(),
                store.getName(),
                store.getDescription(),
                store.getAddress(),
                store.getDetailAddress(),
                store.getPhone(),
                store.getLatitude(),
                store.getLongitude(),
                store.getOpenTime(),
                store.getCloseTime(),
                store.getStatus(),
                store.isAppOrderAvailable(),
                store.isDineInAvailable(),
                store.getAveragePreparationMinutes()
        );
    }
}