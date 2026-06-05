package cafepickuporder.customer.api;

import cafepickuporder.global.security.CustomUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class CustomerController {

    @GetMapping("/api/customers/me")
    public ResponseEntity<Map<String, Object>> me(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return ResponseEntity.ok(Map.of(
                "customerId", userDetails.getCustomerId()
        ));
    }
}