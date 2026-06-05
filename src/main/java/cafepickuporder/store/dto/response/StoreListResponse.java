package cafepickuporder.store.dto.response;

import cafepickuporder.store.domain.OrderType;
import cafepickuporder.store.domain.Store;
import cafepickuporder.store.domain.StoreStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StoreListResponse {

    private Long storeId;
    private String name;
    private String description;
    private String address;
    private StoreStatus status;
    private OrderType orderType;
    private Integer averagePreparationMinutes;

    public static StoreListResponse from(Store store) {
        return new StoreListResponse(
                store.getId(),
                store.getName(),
                store.getDescription(),
                store.getAddress(),
                store.getStatus(),
                store.getOrderType(),
                store.getAveragePreparationMinutes()
        );
    }
}