package cafepickuporder.auth.dto.Response;

import cafepickuporder.customer.domain.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponse {

    private Long customerId;
    private String email;
    private String name;

    public static LoginResponse from(Customer customer) {
        return new LoginResponse(
                customer.getId(),
                customer.getEmail(),
                customer.getName()
        );
    }
}