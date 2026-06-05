package cafepickuporder.auth.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CustomerSignupRequest {

    private String email;
    private String password;
    private String name;
    private String phone;
}