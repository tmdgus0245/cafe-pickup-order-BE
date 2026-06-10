package cafepickuporder.store.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StoreAccountLoginRequest {

    private String email;
    private String password;
}