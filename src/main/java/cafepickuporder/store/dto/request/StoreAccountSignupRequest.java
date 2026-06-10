package cafepickuporder.store.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StoreAccountSignupRequest {

    private Long storeId;
    private String email;
    private String password;
    private String name;
}