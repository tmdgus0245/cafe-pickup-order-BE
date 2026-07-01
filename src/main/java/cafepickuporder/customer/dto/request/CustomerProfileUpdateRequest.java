package cafepickuporder.customer.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CustomerProfileUpdateRequest {

    private String name;
    private String email;
    private String profileImageUrl;
}