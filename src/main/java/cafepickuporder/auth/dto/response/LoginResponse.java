package cafepickuporder.auth.dto.response;

import cafepickuporder.customer.domain.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponse {

    private Long customerId;
    private String email;
    private String name;
    private String accessToken;

    public static LoginResponse of(Customer customer, String accessToken) {
        return new LoginResponse(
                customer.getId(),
                customer.getEmail(),
                customer.getName(),
                accessToken
        );
    }
}