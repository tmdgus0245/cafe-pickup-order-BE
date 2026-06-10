package cafepickuporder.store.dto.response;

import cafepickuporder.store.domain.StoreAccount;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StoreAccountLoginResponse {

    private Long storeAccountId;
    private Long storeId;
    private String email;
    private String name;

    public static StoreAccountLoginResponse from(StoreAccount storeAccount) {
        return new StoreAccountLoginResponse(
                storeAccount.getId(),
                storeAccount.getStore().getId(),
                storeAccount.getEmail(),
                storeAccount.getName()
        );
    }
}