package cafepickuporder.auth.dto.Request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginRequest {

    private String email;
    private String password;
}