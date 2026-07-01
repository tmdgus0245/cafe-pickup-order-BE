package cafepickuporder.customer.dto.response;

import cafepickuporder.customer.domain.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomerProfileResponse {

    private Long customerId;
    private String email;
    private String name;
    private String phone;
    private String profileImageUrl;

    public static CustomerProfileResponse from(Customer customer) {
        return new CustomerProfileResponse(
                customer.getId(),
                customer.getEmail(),
                customer.getName(),
                customer.getPhone(),
                customer.getProfileImageUrl()
        );
    }
}