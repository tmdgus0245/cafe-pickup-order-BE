package cafepickuporder.store.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalTime;

@Getter
@NoArgsConstructor
public class StoreAccountSignupRequest {

    private String email;
    private String password;
    private String name;

    private String storeName;
    private String storeDescription;
    private String storeAddress;
    private String storeDetailAddress;
    private String storePhone;

    private BigDecimal latitude;
    private BigDecimal longitude;

    private LocalTime openTime;
    private LocalTime closeTime;

    private boolean appOrderAvailable;
    private boolean dineInAvailable;
    private Integer averagePreparationMinutes;
}