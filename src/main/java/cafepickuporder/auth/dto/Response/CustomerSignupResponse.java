package cafepickuporder.auth.dto.Response;

import cafepickuporder.customer.domain.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomerSignupResponse {

    private Long customerId;
    private String email;
    private String name;
    private String phone;

    public static CustomerSignupResponse from(Customer customer) {
        return new CustomerSignupResponse(
                customer.getId(),
                customer.getEmail(),
                customer.getName(),
                customer.getPhone()
        );
    }
}