package cafepickuporder.customer.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CustomerPasswordUpdateRequest {

    private String currentPassword;
    private String newPassword;
}