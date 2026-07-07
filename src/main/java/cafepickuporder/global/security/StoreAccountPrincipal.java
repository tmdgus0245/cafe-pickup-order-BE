package cafepickuporder.global.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StoreAccountPrincipal {
    private Long storeAccountId;
    private Long storeId;
}